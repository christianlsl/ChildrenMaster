package com.cm.controller;


import com.cm.dao.MathSet;
import com.cm.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/math")
public class MathController {
    @Autowired
    private MathService mathService;

    @GetMapping("/{level}")
    public MathSet getMathProblems(@PathVariable int level) {
        return mathService.generateMathProblems(level);
    }
}
