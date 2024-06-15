package com.cm.service;

import com.cm.pojo.PoemSet;

import java.util.List;


public interface PoemService {
    List<PoemSet> generateKidPoemProblems();
    List<PoemSet> generateTeenPoemProblems();
    List<Float> getPoemScoresById(long id);
}
