package com.catalpa.pocket.service.impl;

import com.catalpa.pocket.error.ApplicationException;
import com.catalpa.pocket.model.ExamData;
import com.catalpa.pocket.service.ExamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
@Log4j2
@Service("examServiceDispatcher")
public class ExamServiceDispatcher implements ExamService {

    private final ExamService abacusExamService;

    @Autowired
    public ExamServiceDispatcher(@Qualifier("abacusExamService") ExamService abacusExamService) {
        this.abacusExamService = abacusExamService;
    }

    @Override
    public ExamData generateExamData(Integer catelog, Integer level) {

        return switchHandler(catelog).generateExamData(catelog, level);
    }

    @Override
    public ExamData getExamDataById(Integer catelog, Long examId) {
        return switchHandler(catelog).getExamDataById(catelog, examId);
    }

    private ExamService switchHandler(Integer catelog) {
        switch (catelog) {
            case 0:
                return abacusExamService;
            default:
                throw new ApplicationException("50001", "unsupported exam catelog:" + catelog);
        }
    }
}
