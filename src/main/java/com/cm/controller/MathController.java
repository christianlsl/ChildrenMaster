package com.cm.controller;


import com.cm.pojo.MathSet;
import com.cm.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/math")
public class MathController {
    @Autowired
    private MathService mathService;

    @GetMapping("/{level}")
    public MathSet getMathProblems(@PathVariable int level) {
        return mathService.generateMathProblems(level);
    }
    @GetMapping("/score/{id}")
    public List<Float> getMathScoresById(@PathVariable long id) {
        return mathService.getMathScoresById(id);
    }

    @PostMapping("/record/{id}")
    public void addMathExerciseScore(@PathVariable long id, @RequestParam float correctRate) {
        mathService.addMathExerciseScore(id,correctRate);
    }
}
