package com.catalpa.pocket.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("t_exam_paper")
public class ExamPaper extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer catalog;
    private Integer level;
    /**
     * 试卷状态，1正常可用，0不可用
     */
    private Integer status;
    @TableField("total_score")
    private Integer totalScore;
    @TableField("pass_score")
    private Integer passScore;
    /**
     * 0正常，1随机
     */
    @TableField("question_order")
    private Integer questionOrder;
    /**
     * 0普通试卷，1随机生成试卷
     */
    private Integer papertype;
    private String content;
    private String remark;


}
