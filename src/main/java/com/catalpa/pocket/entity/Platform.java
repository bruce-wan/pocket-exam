package com.catalpa.pocket.entity;

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
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_platform")
public class Platform extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId("platform_id")
    private String platformId;
    @TableField("platform_secret")
    private String platformSecret;
    @TableField("platform_scope")
    private String platformScope;
    @TableField("platform_name")
    private String platformName;
    @TableField("platform_icon")
    private String platformIcon;
    @TableField("grant_type")
    private String grantType;
    /**
     * 多个url以逗号分隔
     */
    @TableField("callback_url")
    private String callbackUrl;
    @TableField("expires_in")
    private Integer expiresIn;


}
