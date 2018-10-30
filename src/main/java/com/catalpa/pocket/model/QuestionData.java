package com.catalpa.pocket.model;

import lombok.Data;

import java.util.List;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
@Data
public class QuestionData {

    private Long id;
    private Integer catalog;
    private Integer level;
    private Integer type;
    private Long score;
    private String title;
    private String answer;
    private List<String> options;

}
