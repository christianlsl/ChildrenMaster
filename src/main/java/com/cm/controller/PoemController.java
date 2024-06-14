package com.cm.controller;


import com.cm.pojo.PoemSet;
import com.cm.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/poem")
public class PoemController {
    @Autowired
    private PoemService poemService;

    @GetMapping("/kid")
    public List<PoemSet> getKidPoemProblems (){
        return poemService.generateKidPoemProblems();
    }
    @GetMapping("/teen")
    public List<PoemSet> getTeenPoemProblems(){
        return poemService.generateTeenPoemProblems();
    }
    @GetMapping("/score/{id}")
    public List<Float> getPoemScoresById(@PathVariable long id) {
        return poemService.getPoemScoresById(id);
    }
    @GetMapping("/record/{id}")
    public void addPoemExerciseScore(@PathVariable long id, @RequestParam float correctRate) {
         poemService.addPoemExerciseScore(id,correctRate);
    }

}
