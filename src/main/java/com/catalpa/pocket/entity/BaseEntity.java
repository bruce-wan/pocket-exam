package com.catalpa.pocket.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Data
public class BaseEntity {
    /**
     * 0. no delete, 1. deleted
     */
    @TableField("del_flg")
    @TableLogic
    private Integer delFlg;
    @TableField("created_date")
    private Date createdDate;
    @TableField("updated_date")
    private Date updatedDate;
}
