package com.catalpa.pocket.service;

import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.model.AccessToken;
import com.catalpa.pocket.model.LoginRequest;
import com.catalpa.pocket.model.LoginResponse;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
public interface SocialService {

    AccessToken exchange(Platform platform, String code);

    LoginResponse login(Platform platform, LoginRequest loginRequest);

    AccessToken getAccessToken(Platform platform, Long userId);
}
