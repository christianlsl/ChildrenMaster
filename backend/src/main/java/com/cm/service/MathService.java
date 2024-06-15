package com.cm.service;

import com.cm.pojo.MathSet;

import java.util.List;


public interface MathService {
    MathSet generateMathProblems(int level);
    List<Float> getMathScoresById(long id);
    void addMathExerciseScore(long id,float correctRate);
}
