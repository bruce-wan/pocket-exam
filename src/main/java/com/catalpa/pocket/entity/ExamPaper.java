package com.catalpa.pocket.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.catalpa.pocket.entity.BaseEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bruce_wan
 * @since 2018-11-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_exam_paper")
public class ExamPaper extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user_id")
    private Long userId;
    private String name;
    private Integer catalog;
    private Integer level;
    @TableField("total_score")
    private Integer totalScore;
    @TableField("pass_score")
    private Integer passScore;
    @TableField("user_score")
    private Integer userScore;
    /**
     * 0.不及格，1.及格，2.良好，3.优秀
     */
    @TableField("score_grade")
    private Integer scoreGrade;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
    private Integer duration;
    private String content;
    private String remark;


}
