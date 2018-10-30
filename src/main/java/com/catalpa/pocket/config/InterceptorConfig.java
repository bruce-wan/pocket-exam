package com.catalpa.pocket.config;

import com.catalpa.pocket.handler.TokenHandler;
import com.catalpa.pocket.interceptor.AuthorizeInterceptor;
import com.catalpa.pocket.service.PlatformService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * Created by wanchuan01 on 2018/10/24.
 */
@Configuration
public class InterceptorConfig {

    @Bean
    public MappedInterceptor authorizeInterceptor(@Qualifier("tokenHandlerDispatcher") TokenHandler tokenHandler, PlatformService platformService) {
        String[] includePatterns = {"/api/**"};
        String[] excludePatterns = {};
        return new MappedInterceptor(includePatterns, excludePatterns, new AuthorizeInterceptor(platformService, tokenHandler));
    }
}
