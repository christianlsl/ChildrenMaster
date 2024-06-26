package com.cm.service.impl;

import com.cm.dao.*;
import com.cm.pojo.PoemSet;
import com.cm.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PoemServiceImpl implements PoemService {
    @Autowired
    private PoemRepository poemRepository;

    @Autowired
    private PoemKidRepository poemKidRepository;

    @Autowired
    private PoemTeenRepository poemTeenRepository;
    @Override
    public List<Float> getPoemScoresById(long id){
        List<PoemExerciseScore> scores = poemRepository.findTop10ByUserIdOrderByDateRecordedDesc(id);
        return scores.stream()
                .map(PoemExerciseScore::getCorrectRate) // 获取分数
                .collect(Collectors.toList());
    }

    @Override
    public List<PoemSet> generateKidPoemProblems() {
        List<PoemSet> poemSets = new ArrayList<>();
        Random random = new Random();

        List<PoemKid> poemList = poemKidRepository.findAll();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(poemList.size());
            PoemKid poem = poemList.get(randomIndex);

            int blankVerseIndex = random.nextInt(4);
            String question;
            String answer;
            switch (blankVerseIndex) {
                case 0 -> {
                    question = "_______" + ", " + poem.getVerse2() + ", " + poem.getVerse3() + ", " + poem.getVerse4() + "。";
                    answer = poem.getVerse1();
                }
                case 1 -> {
                    question = poem.getVerse1() + ", " + "_______" + ", " + poem.getVerse3() + ", " + poem.getVerse4() + "。";
                    answer = poem.getVerse2();
                }
                case 2 -> {
                    question = poem.getVerse1() + ", " + poem.getVerse2() + ", " + "_______" + ", " + poem.getVerse4() + "。";
                    answer = poem.getVerse3();
                }
                case 3 -> {
                    question = poem.getVerse1() + ", " + poem.getVerse2() + ", " + poem.getVerse3() + ", " + "_______" + "。";
                    answer = poem.getVerse4();
                }
                default -> throw new IllegalArgumentException("Invalid blank verse index");
            }

            //构造选项
            Set<String> options = new LinkedHashSet<>();
            options.add(answer);
            while (options.size() < 4) {
                int otherRandomIndex = random.nextInt(poemList.size());
                PoemKid otherPoem = poemList.get(otherRandomIndex);
                String otherOption;
                switch (blankVerseIndex) {
                    case 0 -> otherOption = otherPoem.getVerse1();
                    case 1 -> otherOption = otherPoem.getVerse2();
                    case 2 -> otherOption = otherPoem.getVerse3();
                    case 3 -> otherOption = otherPoem.getVerse4();
                    default -> throw new IllegalArgumentException("Invalid blank verse index");
                }
                if (!options.contains(otherOption)) {
                    options.add(otherOption);
                }
            }

            //封装成列表并打乱顺序
            List<String> optionsList = new ArrayList<>(options);
            Collections.shuffle(optionsList);

            //决定答案的字母
            String answerLetter = "";
            if (optionsList.get(0).equals(answer)) answerLetter = "A";
            else if (optionsList.get(1).equals(answer)) answerLetter = "B";
            else if (optionsList.get(2).equals(answer)) answerLetter = "C";
            else if (optionsList.get(3).equals(answer)) answerLetter = "D";

            //格式化选项
            String poemOptions = "A. " + optionsList.get(0) + ", B. " + optionsList.get(1) + ", C. " + optionsList.get(2) + ", D. " + optionsList.get(3);

            PoemSet poemSet = new PoemSet();
            poemSet.setTitle(poem.getTitle());
            poemSet.setPoemVerses(question);
            poemSet.setPoemOptions(poemOptions);
            poemSet.setAnswer(answerLetter);

            poemSets.add(poemSet);
        }

        return poemSets;
    }

    @Override
    public List<PoemSet> generateTeenPoemProblems() {
        List<PoemSet> poemSets = new ArrayList<>();
        Random random = new Random();

        List<PoemTeen> poemList = poemTeenRepository.findAll();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(poemList.size());
            PoemTeen poem = poemList.get(randomIndex);

            int blankVerseIndex = random.nextInt(4);
            String question;
            String answer;
            switch (blankVerseIndex) {
                case 0 -> {
                    question = "_______" + ", " + poem.getVerse2() + ", " + poem.getVerse3() + ", " + poem.getVerse4() + "。";
                    answer = poem.getVerse1();
                }
                case 1 -> {
                    question = poem.getVerse1() + ", " + "_______" + ", " + poem.getVerse3() + ", " + poem.getVerse4() + "。";
                    answer = poem.getVerse2();
                }
                case 2 -> {
                    question = poem.getVerse1() + ", " + poem.getVerse2() + ", " + "_______" + ", " + poem.getVerse4() + "。";
                    answer = poem.getVerse3();
                }
                case 3 -> {
                    question = poem.getVerse1() + ", " + poem.getVerse2() + ", " + poem.getVerse3() + ", " + "_______" + "。";
                    answer = poem.getVerse4();
                }
                default -> throw new IllegalArgumentException("Invalid blank verse index");
            }

            //构造选项
            Set<String> options = new LinkedHashSet<>();
            options.add(answer);
            while (options.size() < 4) {
                int otherRandomIndex = random.nextInt(poemList.size());
                PoemTeen otherPoem = poemList.get(otherRandomIndex);
                String otherOption;
                switch (blankVerseIndex) {
                    case 0 -> otherOption = otherPoem.getVerse1();
                    case 1 -> otherOption = otherPoem.getVerse2();
                    case 2 -> otherOption = otherPoem.getVerse3();
                    case 3 -> otherOption = otherPoem.getVerse4();
                    default -> throw new IllegalArgumentException("Invalid blank verse index");
                }
                if (!options.contains(otherOption)) {
                    options.add(otherOption);
                }
            }

            //封装成列表并打乱顺序
            List<String> optionsList = new ArrayList<>(options);
            Collections.shuffle(optionsList);

            //决定答案的字母
            String answerLetter = "";
            if (optionsList.get(0).equals(answer)) answerLetter = "A";
            else if (optionsList.get(1).equals(answer)) answerLetter = "B";
            else if (optionsList.get(2).equals(answer)) answerLetter = "C";
            else if (optionsList.get(3).equals(answer)) answerLetter = "D";

            //格式化选项
            String poemOptions = "A. " + optionsList.get(0) + ", B. " + optionsList.get(1) + ", C. " + optionsList.get(2) + ", D. " + optionsList.get(3);

            PoemSet poemSet = new PoemSet();
            poemSet.setTitle(poem.getTitle());
            poemSet.setPoemVerses(question);
            poemSet.setPoemOptions(poemOptions);
            poemSet.setAnswer(answerLetter);

            poemSets.add(poemSet);
        }

        return poemSets;
    }
    @Override
    public void addPoemExerciseScore(long id,float correctRate) {
        PoemExerciseScore score = new PoemExerciseScore();
        score.setUserId(id);
        score.setCorrectRate(correctRate);
        score.setDateRecorded(LocalDateTime.now());

        poemRepository.save(score);
    }
}
