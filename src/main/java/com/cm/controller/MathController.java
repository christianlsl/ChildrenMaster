package com.cm.controller;


import com.cm.dto.UserDTO;
import com.cm.pojo.MathSet;
import com.cm.service.MathService;
import com.cm.utils.UserHolder;
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
    @GetMapping("/score")
    public List<Float> getMathScoresById() {
        UserDTO user = UserHolder.getUser();
        long id = user.getId();
        return mathService.getMathScoresById(id);
    }

    @PostMapping("/record")
    public void addMathExerciseScore(@RequestParam float correctRate) {
        UserDTO user = UserHolder.getUser();
        long id = user.getId();
        mathService.addMathExerciseScore(id,correctRate);
    }
}
