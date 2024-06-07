package com.cm.controller;


import com.cm.pojo.PoemSet;
import com.cm.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class PoemController {
    @Autowired
    private PoemService poemService;

    @GetMapping("/user/poem/kid")
    public List<PoemSet> getKidPoemProblems (){
        return poemService.generateKidPoemProblems();
    }
    @GetMapping("/user/poem/teen")
    public List<PoemSet> getTeenPoemProblems(){
        return poemService.generateTeenPoemProblems();
    }
    @GetMapping("/user/score/poem/{id}")
    public List<Float> getPoemScoresById(@PathVariable long id) {
        return poemService.getPoemScoresById(id);
    }
}
