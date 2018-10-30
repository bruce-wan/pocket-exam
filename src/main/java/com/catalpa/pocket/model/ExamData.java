package com.catalpa.pocket.model;

import lombok.Data;

import java.util.List;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
@Data
public class ExamData {

    private Long id;
    private String name;
    private Integer catalog;
    private Integer level;
    private Integer totalScore;
    private Integer passScore;
    private String remark;
    private List<QuestionData> questionDatas;
}
