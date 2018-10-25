package com.catalpa.pocket.interceptor;

import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.handler.TokenHandler;
import com.catalpa.pocket.model.AccessTokenPayload;
import com.catalpa.pocket.service.PlatformService;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanchuan01 on 2018/10/24.
 */
@Log4j2
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    private final PlatformService platformService;
    private final TokenHandler tokenHandler;

    public AuthorizeInterceptor(PlatformService platformService, TokenHandler tokenHandler) {
        this.platformService = platformService;
        this.tokenHandler = tokenHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        Assert.hasText(authorization, "authorization must not be null, empty, or blank");

        AccessTokenPayload accessTokenPayload = tokenHandler.validate(authorization);

        String platformId = accessTokenPayload.getPlatformId();
        Platform platform = platformService.getPlatformById(platformId);
        request.setAttribute("platform", platform);

        return super.preHandle(request, response, handler);
    }
}
