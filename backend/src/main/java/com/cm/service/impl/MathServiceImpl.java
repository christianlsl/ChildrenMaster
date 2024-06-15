package com.cm.service.impl;

import com.cm.dao.*;
import com.cm.pojo.MathProblem;
import com.cm.pojo.MathSet;
import com.cm.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//都是正整数且除法的结果没有余数 1表示结果在20以内的加减法。2表示结果在100以内两位数的加减。
// 3表示生成一个结果在100以内乘除。4表示结果在1000以内加减乘除。
@Service
public class MathServiceImpl implements MathService {
    @Autowired
    private MathRepository mathRepository;

    @Override
    public List<Float> getMathScoresById(long id){
        List<MathExerciseScore> scores = mathRepository.findTop10ByUserIdOrderByDateRecordedDesc(id);
        return scores.stream()
                .map(MathExerciseScore::getCorrectRate) // 获取分数
                .collect(Collectors.toList());
    }

    @Override
    public MathSet generateMathProblems(int level) {
        MathSet mathSet = new MathSet();
        List<MathProblem> problems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            problems.add(generateMathProblem(level));
        }
        mathSet.setProblems(problems);
        return mathSet;
    }

    private MathProblem generateMathProblem(int level) {
        Random random = new Random();
        int num1, num2, answer;
        char operator;

        do {
            switch (level) {
                case 1:
                    num1 = random.nextInt(21);
                    num2 = random.nextInt(21);
                    operator = random.nextBoolean() ? '+' : '-';
                    break;
                case 2:
                    num1 = random.nextInt(91) + 10; // 10 to 100
                    num2 = random.nextInt(91) + 10; // 10 to 100
                    operator = random.nextBoolean() ? '+' : '-';
                    break;
                case 3:
                    num1 = random.nextInt(99) + 2; // 2 to 100
                    num2 = random.nextInt(99) + 2; // 2 to 100
                    operator = random.nextBoolean() ? '*' : '/';
                    break;
                case 4:
                    num1 = random.nextInt(991) +10; // 10 to 1000
                    num2 = random.nextInt(991) +10; // 10 to 1000
                    operator = switch (random.nextInt(10)) {
                        case 0,1,2,3 -> '*';
                        case 4,5,6,7 -> '/';
                        case 8 -> '+';
                        case 9 -> '-';
                        default -> throw new IllegalArgumentException("Invalid operator");
                    };
                    break;
                default:
                    throw new IllegalArgumentException("Invalid level: " + level);
            }

            switch (operator) {
                case '+':
                    answer = num1 + num2;
                    break;
                case '-':
                    answer = num1 - num2;
                    break;
                case '*':
                    answer = num1 * num2;
                    break;
                case '/':
                    if ( num1 % num2 != 0) {
                        continue;
                    }
                    answer = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }


            if (level == 1 && (answer < 0 || answer > 20)) {
                continue;
            } else if (level == 2 && (answer < 0 || answer > 100)) {
                continue;
            } else if (level == 3 && (answer < 2 || answer > 1000)) {
                continue;
            } else if (level == 4 && (answer < 0 || answer > 10000)) {
                continue;
            }
            break;
        } while (true);

        MathProblem problem = new MathProblem();
        problem.setQuestion(num1 + " " + operator + " " + num2 + " = ?");
        problem.setAnswer(answer);
        return problem;
    }
    @Override
    public void addMathExerciseScore( long id,float correctRate) {
        MathExerciseScore score = new MathExerciseScore();
        score.setUserId(id);
        score.setCorrectRate(correctRate);
        score.setDateRecorded(LocalDateTime.now());

        mathRepository.save(score);
    }

}
