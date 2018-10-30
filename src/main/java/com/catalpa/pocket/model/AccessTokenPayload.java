package com.catalpa.pocket.model;

import lombok.Data;

/**
 * Created by wanchuan01 on 2018/10/24.
 */
@Data
public class AccessTokenPayload {
    private Long userId;
    private String platformId;
    private String scope;
}
