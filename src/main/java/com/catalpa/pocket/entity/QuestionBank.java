package com.catalpa.pocket.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
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
@TableName("t_question_bank")
public class QuestionBank extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer catalog;
    private Integer level;
    /**
     * 1 - SingleChoice, 2 - MultipleChoice, 3 - Judgment, 4 - BlankFill
     */
    private Integer type;
    private String title;
    private String answer;
    private String content;
    private String remark;
    private Integer score;


}
