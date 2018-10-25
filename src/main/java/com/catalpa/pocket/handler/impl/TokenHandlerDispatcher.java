package com.catalpa.pocket.handler.impl;

import com.catalpa.pocket.error.ApplicationException;
import com.catalpa.pocket.error.UnAuthorizedAccessException;
import com.catalpa.pocket.handler.TokenHandler;
import com.catalpa.pocket.model.AccessToken;
import com.catalpa.pocket.model.AccessTokenPayload;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by wanchuan01 on 2018/10/24.
 */
@Log4j2
@Component("tokenHandlerDispatcher")
public class TokenHandlerDispatcher implements TokenHandler {
    private final TokenHandler jwtTokenHandler;
    private final TokenHandler basicTokenHandler;

    @Autowired
    public TokenHandlerDispatcher(@Qualifier("jwtTokenHandler") TokenHandler jwtTokenHandler,
                                  @Qualifier("basicTokenHandler") TokenHandler basicTokenHandler) {
        this.jwtTokenHandler = jwtTokenHandler;
        this.basicTokenHandler = basicTokenHandler;
    }

    @Override
    public AccessToken generate(String tokenType, int loginExpires, AccessTokenPayload accessTokenPayload) {
        AccessToken accessToken = null;
        switch (tokenType) {
            case "Basic":
                accessToken = basicTokenHandler.generate(tokenType, loginExpires, accessTokenPayload);
                break;
            case "Bearer":
                accessToken = jwtTokenHandler.generate(tokenType, loginExpires, accessTokenPayload);
                break;
            default:
                String errorMessage = "unsupported token type, can not generate accessToken";
                log.error(errorMessage);
                throw new ApplicationException("500", errorMessage);
        }
        return accessToken;
    }

    @Override
    public AccessTokenPayload validate(String authorization) {
        String[] params = authorization.split(" ");
        if (params.length == 2) {
            String type = params[0];
            String token = params[1];
            AccessTokenPayload accessTokenPayload = null;
            switch (type) {
                case "Bearer":
                    accessTokenPayload = jwtTokenHandler.validate(token);
                    break;
                case "Basic":
                    accessTokenPayload = basicTokenHandler.validate(token);
                    break;
                default:
                    String errorMessage = "unknown authorize type: " + type;
                    log.error(errorMessage);
                    throw new UnAuthorizedAccessException("40101", errorMessage);
            }
            return accessTokenPayload;
        } else {
            String errorMessage = "invalid authorization: " + authorization;
            log.error(errorMessage);
            throw new UnAuthorizedAccessException("40102", errorMessage);
        }
    }
}
