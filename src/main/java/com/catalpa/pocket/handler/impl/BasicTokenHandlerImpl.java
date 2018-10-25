package com.catalpa.pocket.handler.impl;

import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.error.ApplicationException;
import com.catalpa.pocket.error.UnAuthorizedAccessException;
import com.catalpa.pocket.handler.TokenHandler;
import com.catalpa.pocket.model.AccessToken;
import com.catalpa.pocket.model.AccessTokenPayload;
import com.catalpa.pocket.service.PlatformService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by wanchuan01 on 2018/10/24.
 */
@Log4j2
@Component("basicTokenHandler")
public class BasicTokenHandlerImpl implements TokenHandler {

    private final PlatformService platformService;

    @Autowired
    public BasicTokenHandlerImpl(PlatformService platformService) {
        this.platformService = platformService;
    }

    @Override
    public AccessToken generate(String tokenType, int loginExpires, AccessTokenPayload accessTokenPayload) {
        Platform platform = platformService.getPlatformById(accessTokenPayload.getPlatformId());

        String baseStr = String.format("%s:%s", platform.getPlatformId(), platform.getPlatformSecret());
        try {
            String token = Base64.encodeBase64String(baseStr.getBytes("utf-8"));

            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(token);
            accessToken.setExpiresIn(loginExpires);
            accessToken.setTokenType(tokenType);

            return accessToken;
        } catch (UnsupportedEncodingException e) {
            String errorMessage = "decode authorization found UnsupportedEncodingException: " + e;
            log.error(errorMessage);
            throw new ApplicationException("500", errorMessage);
        }
    }

    @Override
    public AccessTokenPayload validate(String authorization) {
        try {
            String token = new String(Base64.decodeBase64(authorization), "utf-8");
            String[] split = token.split(":");
            if (split.length == 2) {
                String platformId = split[0];
                String platformSecret = split[1];

                Platform platform = platformService.getPlatformById(platformId);
                if (platformSecret.equals(platform.getPlatformSecret())) {
                    AccessTokenPayload accessTokenPayload = new AccessTokenPayload();
                    accessTokenPayload.setPlatformId(platformId);
                    accessTokenPayload.setScope(platform.getPlatformScope());
                    return accessTokenPayload;
                }
            }
            String errorMessage = "invalid authorization: " + authorization;
            log.error(errorMessage);
            throw new UnAuthorizedAccessException("40102", errorMessage);
        } catch (UnsupportedEncodingException e) {
            String errorMessage = "decode authorization found UnsupportedEncodingException: " + e;
            log.error(errorMessage);
            throw new ApplicationException("500", errorMessage);
        }
    }
}
