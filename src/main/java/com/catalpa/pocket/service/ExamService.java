package com.catalpa.pocket.service;

import com.catalpa.pocket.model.ExamData;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
public interface ExamService {

    ExamData generateExamData(Integer catelog, Integer level);
}
