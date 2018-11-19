package com.catalpa.pocket.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
@Data
public class ExamData {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    private String startTimeStr;
    private Date endTime;
    private String endTimeStr;
    private Integer duration;
    private String content;
    private String remark;
    private List<QuestionData> questionDatas;

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getStartTimeStr() {
        if (startTime != null) {
            return LocalDateTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault()).format(DATE_TIME_FORMATTER);
        } else {
            return null;
        }
    }

    public String getEndTimeStr() {
        if (endTime != null) {
            return LocalDateTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault()).format(DATE_TIME_FORMATTER);
        } else {
            return null;
        }
    }
}
