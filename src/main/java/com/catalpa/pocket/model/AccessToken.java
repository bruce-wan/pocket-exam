package com.catalpa.pocket.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccessToken {
    private String accessToken;
    private long expiresIn;
    private String refreshToken;
    private String tokenType;
}
