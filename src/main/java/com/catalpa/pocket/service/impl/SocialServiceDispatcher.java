package com.catalpa.pocket.service.impl;

import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.error.ApplicationException;
import com.catalpa.pocket.model.AccessToken;
import com.catalpa.pocket.model.LoginRequest;
import com.catalpa.pocket.model.LoginResponse;
import com.catalpa.pocket.service.SocialService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Log4j2
@Service("socialServiceDispatcher")
public class SocialServiceDispatcher implements SocialService {

    private final SocialService wechatSocialService;

    @Autowired
    public SocialServiceDispatcher(@Qualifier("wechatSocialService") SocialService wechatSocialService) {
        this.wechatSocialService = wechatSocialService;
    }

    @Override
    public AccessToken exchange(Platform platform, String code) {
        return null;
    }

    @Override
    public LoginResponse login(Platform platform, LoginRequest loginRequest) {
        LoginResponse loginResponse = null;
        switch (platform.getPlatformName()) {
            case "wechat":
                loginResponse = wechatSocialService.login(platform, loginRequest);
                break;
            default:
                throw new ApplicationException("500", "50001", "unsupported platform:" + platform);
        }
        return loginResponse;
    }

    @Override
    public AccessToken getAccessToken(Platform platform, Long userId) {
        return null;
    }

}
