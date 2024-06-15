package com.cm.controller;


import com.cm.pojo.MathSet;
import com.cm.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class MathController {
    @Autowired
    private MathService mathService;

    @GetMapping("/user/math/{level}")
    public MathSet getMathProblems(@PathVariable int level) {
        return mathService.generateMathProblems(level);
    }
    @GetMapping("/user/score/math/{id}")
    public List<Float> getMathScoresById(@PathVariable long id) {
        return mathService.getMathScoresById(id);
    }
}
