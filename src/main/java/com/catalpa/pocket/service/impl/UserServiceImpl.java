package com.catalpa.pocket.service.impl;

import com.catalpa.pocket.config.ShiroProperties;
import com.catalpa.pocket.entity.UserInfo;
import com.catalpa.pocket.mapper.UserInfoMapper;
import com.catalpa.pocket.model.UserData;
import com.catalpa.pocket.service.UserService;
import com.catalpa.pocket.util.RandomUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Log4j2
@Service
@EnableConfigurationProperties(value = {ShiroProperties.class})
public class UserServiceImpl implements UserService {

    private final ShiroProperties shiroProperties;
    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserServiceImpl(ShiroProperties shiroProperties, UserInfoMapper userInfoMapper) {
        this.shiroProperties = shiroProperties;
        this.userInfoMapper = userInfoMapper;

        log.debug("getHashAlgorithmName: =====>"+shiroProperties.getHashAlgorithmName());
        log.debug("getHashIterations: =====>"+shiroProperties.getHashIterations());
    }

    @Override
    public UserData createUser(UserData userData) throws UnsupportedEncodingException {
        UserInfo userInfo = new UserInfo();

        String salt = RandomUtil.generateString(false, true, true, null, 10);

        SimpleHash simpleHash = new SimpleHash(shiroProperties.getHashAlgorithmName(), userData.getPassword(), ByteSource.Util.bytes(userData.getUsername()+salt), shiroProperties.getHashIterations());

        userInfo.setUsername(userData.getUsername());
        userInfo.setPassword(simpleHash.toString());
        userInfo.setNickName(userData.getNickName());
        userInfo.setGender(userData.getGender());
        userInfo.setHeadImgUrl(userData.getHeadImgUrl());
        userInfo.setSalt(salt);
        Integer inserted = userInfoMapper.insert(userInfo);

        if (inserted == 1) {
            Long userId = userInfo.getId();

            log.debug("new user id: ====>" + userId);

            return userData;
        } else {
            throw new RuntimeException("insert userinfo error");
        }
    }
}
