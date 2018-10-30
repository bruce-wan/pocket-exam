package com.catalpa.pocket.handler;

import com.catalpa.pocket.model.AccessToken;
import com.catalpa.pocket.model.AccessTokenPayload;

/**
 * Created by wanchuan01 on 2018/10/24.
 */
public interface TokenHandler {

    AccessToken generate(String tokenType, int loginExpires, AccessTokenPayload accessTokenPayload);
    AccessTokenPayload validate(String authorization);
}
