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
 * @author ${author}
 * @since 2018-10-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_exam_history")
public class ExamHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("paper_id")
    private Long paperId;
    @TableField("user_id")
    private Long userId;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
    @TableField("total_score")
    private Integer totalScore;
    @TableField("pass_score")
    private Integer passScore;
    @TableField("user_score")
    private Integer userScore;
    private String content;
    private String remark;


}
