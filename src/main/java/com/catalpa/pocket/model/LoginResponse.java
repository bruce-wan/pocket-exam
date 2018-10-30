package com.catalpa.pocket.model;

import lombok.Data;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Data
public class LoginResponse {

    private UserData userData;
    private AccessToken accessToken;
}
