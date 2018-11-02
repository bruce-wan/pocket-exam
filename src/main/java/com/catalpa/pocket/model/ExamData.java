package com.catalpa.pocket.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
@Data
public class ExamData {

    private Long id;
    private Long userId;
    private String name;
    private Integer catalog;
    private Integer level;
    private Integer totalScore;
    private Integer passScore;
    private Integer userScore;
    private Integer scoreGrade;
    private Date startTime;
    private Date endTime;
    private Integer duration;
    private String content;
    private String remark;
    private List<QuestionData> questionDatas;
}
