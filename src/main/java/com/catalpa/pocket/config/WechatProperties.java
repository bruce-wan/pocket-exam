package com.catalpa.pocket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by wanchuan01 on 2018/10/23.
 */

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
@PropertySource(value = {"classpath:wechat.properties"})
public class WechatProperties {

    private String miniProgramAppid;
    private String miniProgramAppsecret;

    private String publicAccountAppid;
    private String publicAccountAppsecret;
    private String publicAccountToken;

    private String tokenUrl;
    private String userInfoUrl;
    private String jscode2sessionUrl;
    private String oauthAuthorizeUrl;
    private String oauthAccessTokenUrl;
}
