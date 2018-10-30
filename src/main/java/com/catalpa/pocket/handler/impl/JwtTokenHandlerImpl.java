package com.catalpa.pocket.handler.impl;

import com.catalpa.pocket.handler.TokenHandler;
import com.catalpa.pocket.model.AccessToken;
import com.catalpa.pocket.model.AccessTokenPayload;
import com.catalpa.pocket.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Created by wanchuan01 on 2018/10/24.
 */
@Log4j2
@Component("jwtTokenHandler")
public class JwtTokenHandlerImpl implements TokenHandler {
    @Override
    public AccessToken generate(String tokenType, int loginExpires, AccessTokenPayload accessTokenPayload) {
        AccessToken accessToken = new AccessToken();
        accessToken.setExpiresIn(loginExpires);
        accessToken.setAccessToken(JwtTokenUtil.buildToken(loginExpires, accessTokenPayload));
        accessToken.setTokenType(tokenType);
        return accessToken;
    }

    @Override
    public AccessTokenPayload validate(String authorization) {
        return JwtTokenUtil.validateToken(authorization);
    }
}
