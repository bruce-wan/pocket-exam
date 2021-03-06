package com.catalpa.pocket.controller;

import com.catalpa.pocket.model.ExamData;
import com.catalpa.pocket.service.ExamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
@Log4j2
@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(@Qualifier("examServiceDispatcher") ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ExamData generateExam(@RequestParam Integer catalog, @RequestParam Integer level) {
        return examService.generateExamData(catalog, level);
    }

    @GetMapping("/{examId}")
    public ExamData getExamById(@PathVariable Long examId, @RequestParam Integer catalog) {
        return examService.getExamDataById(catalog, examId);
    }
}
