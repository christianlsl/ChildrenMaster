package com.cm.controller;


import com.cm.dto.UserDTO;
import com.cm.pojo.PoemSet;
import com.cm.service.PoemService;
import com.cm.utils.UserHolder;
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
    @GetMapping("/score")
    public List<Float> getPoemScoresById() {
        UserDTO user = UserHolder.getUser();
        long id = user.getId();
        return poemService.getPoemScoresById(id);
    }
    @PostMapping("/record")
    public void addPoemExerciseScore(@RequestParam float correctRate) {
        UserDTO user = UserHolder.getUser();
        long id = user.getId();
         poemService.addPoemExerciseScore(id,correctRate);
    }

}
