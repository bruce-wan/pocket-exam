package com.catalpa.pocket.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catalpa.pocket.config.WechatProperties;
import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.entity.UserIdentity;
import com.catalpa.pocket.error.ApplicationException;
import com.catalpa.pocket.handler.TokenHandler;
import com.catalpa.pocket.model.*;
import com.catalpa.pocket.service.SocialService;
import com.catalpa.pocket.service.UserService;
import com.catalpa.pocket.service.WechatService;
import com.catalpa.pocket.util.CryptoUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Log4j2
@Service("wechatSocialService")
@EnableConfigurationProperties(value = {WechatProperties.class})
public class SocialServiceWechatImpl implements SocialService, WechatService {

    private static final String MINIPROGRAM_SESSIONKEY = "miniprogram_session_key";

    private final WechatProperties wechatProperties;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final TokenHandler tokenHandler;
    private final UserService userService;

    @Autowired
    public SocialServiceWechatImpl(@Qualifier("tokenHandlerDispatcher") TokenHandler tokenHandler, WechatProperties wechatProperties, RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate, UserService userService) {
        this.tokenHandler = tokenHandler;
        this.wechatProperties = wechatProperties;
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
        this.userService = userService;

        if (log.isDebugEnabled()) {
            log.debug("getMiniProgramAppid: =====>" + wechatProperties.getMiniProgramAppid());
            log.debug("getMiniProgramAppsecret: =====>" + wechatProperties.getMiniProgramAppsecret());
        }
    }

    @Override
    public AccessToken exchange(Platform platform, String code) {
        return null;
    }

    @Override
    public LoginResponse login(Platform platform, LoginRequest loginRequest) {
        String code = loginRequest.getCode();
        String encryptedData = loginRequest.getEncryptedData();
        String iv = loginRequest.getIv();

        UserData userData = getUserData(platform.getPlatformId(), code, encryptedData, iv);

        AccessTokenPayload accessTokenPayload = new AccessTokenPayload();
        accessTokenPayload.setPlatformId(platform.getPlatformId());
        accessTokenPayload.setScope(platform.getPlatformScope());
        accessTokenPayload.setUserId(userData.getId());
        AccessToken accessToken = tokenHandler.generate("Bearer", 7200, accessTokenPayload);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserData(userData);
        loginResponse.setAccessToken(accessToken);

        return loginResponse;
    }

    @Override
    public AccessToken getAccessToken(Platform platform, Long userId) {
        return null;
    }

    @Override
    public String getPublicAccountToken() {
        return wechatProperties.getPublicAccountToken();
    }

    private UserData getUserData(String platformId, String code, String encryptedData, String iv) {
        JSONObject sessionKeyJson;
        if (StringUtils.isNotBlank(code)) {
            sessionKeyJson = getSessionKey(code);
            redisTemplate.opsForValue().set(MINIPROGRAM_SESSIONKEY, sessionKeyJson.toJSONString());
        } else {
            sessionKeyJson = JSONObject.parseObject(redisTemplate.opsForValue().get(MINIPROGRAM_SESSIONKEY));
        }
//        JSONObject sessionKeyJson = new JSONObject();
//        sessionKeyJson.put("openid", "o4j8X0Q-LTnxp0o_y1ops0pwaWsM");
//        sessionKeyJson.put("unionid", "od63W05TovzFY9Xwtm-ebOQ2WhcY");
//        sessionKeyJson.put("session_key", "session_key");

        if (log.isDebugEnabled()) {
            log.debug("input encryptedData:" + encryptedData + " and iv: " + iv);
        }

        if (sessionKeyJson == null) {
            String message = "can not get available session key";
            log.error(message);
            throw new ApplicationException("5003", message);
        }
        String openid = sessionKeyJson.getString("openid");
        String sessionKey = sessionKeyJson.getString("session_key");
        String unionid = sessionKeyJson.getString("unionid");

        UserData userData = userService.getUserByThirdPartyId(platformId, StringUtils.isNotBlank(unionid) ? unionid : openid);
        if (userData == null) {
            if (StringUtils.isNotBlank(encryptedData) && StringUtils.isNotBlank(iv)) {
//                String skey = CryptoUtil.getHashSHA1Str(sessionKey, "utf8");
                String decrypt = CryptoUtil.aesCbcDecrypt(encryptedData, sessionKey, iv);
//                String decrypt = "{\"openId\":\"o4j8X0Q-LTnxp0o_y1ops0pwaWsM\",\"nickName\":\"( *¯ ꒳¯*)ok!!\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"Albania\",\"avatarUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqQ4Qib7yhHB8zNwvibuIchefLAXyLjTj9vb2lRWWUwONrDYMIAxlpRoibzq21eUcicMUJqoxZNNXVtvA/132\",\"unionId\":\"od63W05TovzFY9Xwtm-ebOQ2WhcY\",\"watermark\":{\"timestamp\":1531215296,\"appid\":\"wx65041ea5429cf1ec\"}}";
                log.info(decrypt);
                try {
                    userData = JSONObject.parseObject(decrypt, UserData.class);
                } catch (Exception e) {
                    String message = "decrypt data can not convert to user data";
                    log.error(message);
                    throw new ApplicationException("5004", message);
                }
                userService.createUser(platformId, userData);
            }
        } else {
            List<UserIdentity> userIdentities = userData.getUserIdentities();
            boolean noneMatchIdentity = userIdentities.stream().noneMatch(userIdentity -> platformId.equals(userIdentity.getPlatformId()));
            if (noneMatchIdentity) {
                UserIdentity userIdentity = userService.addUserIdentity(userData.getId(), openid, platformId);
                if (userIdentity != null) {
                    userIdentities.add(userIdentity);
                }
            }
        }
        return userData;
    }

    private JSONObject getSessionKey(String wxcode) {
        String appid = wechatProperties.getMiniProgramAppid();
        String appsecret = wechatProperties.getMiniProgramAppsecret();

        String getSessionKeyUrl = wechatProperties.getJscode2sessionUrl();

        Assert.hasText(getSessionKeyUrl, "getSessionKeyUrl must not be empty");
        Assert.hasText(appid, "appid must not be empty");
        Assert.hasText(appsecret, "appSecret must not be empty");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(String.format(getSessionKeyUrl, appid, appsecret, wxcode), String.class);
        JSONObject sessionKey = JSONObject.parseObject(responseEntity.getBody());
        if (sessionKey.getString("errcode") != null) {
            String errorMessage = "get session key error: " + sessionKey.getString("errmsg");
            log.error(errorMessage);
            throw new ApplicationException("5001", errorMessage);
        }
        return sessionKey;
    }
}
