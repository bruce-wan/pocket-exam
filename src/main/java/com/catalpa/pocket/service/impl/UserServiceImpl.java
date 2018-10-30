package com.catalpa.pocket.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.catalpa.pocket.config.ShiroProperties;
import com.catalpa.pocket.entity.UserIdentity;
import com.catalpa.pocket.entity.UserInfo;
import com.catalpa.pocket.error.ApplicationException;
import com.catalpa.pocket.mapper.UserIdentityMapper;
import com.catalpa.pocket.mapper.UserInfoMapper;
import com.catalpa.pocket.model.UserData;
import com.catalpa.pocket.service.UserService;
import com.catalpa.pocket.util.CryptoUtil;
import com.catalpa.pocket.util.RandomUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Log4j2
@Service
@EnableConfigurationProperties(value = {ShiroProperties.class})
public class UserServiceImpl implements UserService {

    private final ShiroProperties shiroProperties;
    private final UserInfoMapper userInfoMapper;
    private final UserIdentityMapper userIdentityMapper;

    @Autowired
    public UserServiceImpl(ShiroProperties shiroProperties, UserInfoMapper userInfoMapper, UserIdentityMapper userIdentityMapper) {
        this.shiroProperties = shiroProperties;
        this.userInfoMapper = userInfoMapper;
        this.userIdentityMapper = userIdentityMapper;

        if (log.isDebugEnabled()) {
            log.debug("getHashAlgorithmName: =====>" + shiroProperties.getHashAlgorithmName());
            log.debug("getHashIterations: =====>" + shiroProperties.getHashIterations());
        }
    }

    @Override
    @Transactional
    public UserData createUser(String platformId, UserData userData) {
        UserInfo userInfo = new UserInfo();

        String username = userData.getUsername();
        if (StringUtils.isBlank(username)) {
            String encryptStr = platformId + System.currentTimeMillis();
            username = CryptoUtil.encrypt16MD5(encryptStr);
        }
        userInfo.setUsername(username);

        EntityWrapper<UserInfo> wrapper = new EntityWrapper<>();
        wrapper.setEntity(userInfo);
        Integer count = userInfoMapper.selectCount(wrapper);
        if (count != 0) {
            String message = "duplicated username: " + username;
            log.error(message);
            throw new ApplicationException("500", "50002", message);
        }

        String salt = RandomUtil.generateString(false, true, true, null, 10);
        String password = StringUtils.isNotBlank(userData.getPassword()) ? userData.getPassword() : "123456";
        SimpleHash simpleHash = new SimpleHash(shiroProperties.getHashAlgorithmName(), password, ByteSource.Util.bytes(username + salt), shiroProperties.getHashIterations());

        userInfo.setPassword(simpleHash.toString());
        userInfo.setNickName(userData.getNickName());
        userInfo.setGender(userData.getGender());
        userInfo.setHeadImgUrl(userData.getHeadImgUrl());
        userInfo.setSalt(salt);
        try {
            userInfoMapper.insert(userInfo);
        } catch (Exception e) {
            String message = String.format("can create new user with data: %s", userData);
            log.error(message);
            throw new ApplicationException("500", "50002", message, e);
        }

        Long userId = userInfo.getId();
        userData.setId(userId);
        if (log.isDebugEnabled()) {
            log.debug("new user id: ====>" + userId);
        }

        List<UserIdentity> userIdentities = new ArrayList<>();
        UserIdentity openIdentity = new UserIdentity();
        openIdentity.setUserId(userId);
        openIdentity.setPlatformId(platformId);
        openIdentity.setThirdPartyId(userData.getOpenId());
        userIdentityMapper.insert(openIdentity);
        userIdentities.add(openIdentity);

        String unionId = userData.getUnionId();
        if (StringUtils.isNotBlank(unionId)) {
            UserIdentity unionIdentity = new UserIdentity();
            unionIdentity.setUserId(userId);
            unionIdentity.setPlatformId(platformId);
            unionIdentity.setThirdPartyId(unionId);
            userIdentityMapper.insert(unionIdentity);
            userIdentities.add(unionIdentity);
        }

        userData.setUserIdentities(userIdentities);
        return userData;
    }

    @Override
    public UserData getUserById(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);

        if (userInfo != null) {
            UserData userData = new UserData();

            userData.setId(userId);
            userData.setUsername(userInfo.getUsername());
            userData.setNickName(userInfo.getNickName());
            userData.setGender(userInfo.getGender());
            userData.setHeadImgUrl(userInfo.getHeadImgUrl());

            EntityWrapper<UserIdentity> wrapper = new EntityWrapper<UserIdentity>();
            UserIdentity userIdentity = new UserIdentity();
            userIdentity.setUserId(userId);
            wrapper.setEntity(userIdentity);
            List<UserIdentity> userIdentities = userIdentityMapper.selectList(wrapper);
            if (userIdentities != null && !userIdentities.isEmpty()) {
                userData.setUserIdentities(userIdentities);
            }
            return userData;
        } else {
            String message = String.format("Can not found user with userid = %d", userId);
            log.error(message);
            throw new RuntimeException(message);
        }

    }

    @Override
    public UserData getUserByThirdPartyId(String platformId, String thirdPartyId) {
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setPlatformId(platformId);
        userIdentity.setThirdPartyId(thirdPartyId);
        EntityWrapper<UserIdentity> wrapper = new EntityWrapper<>(userIdentity);
        List<UserIdentity> identities = userIdentityMapper.selectList(wrapper);

        if (identities != null && !identities.isEmpty()) {
            if (identities.size() > 1) {
                String message = "invalid user identity with platform is " + platformId + " and thirdPartyId is " + thirdPartyId;
                log.error(message);
                throw new ApplicationException("500", "50003", message);
            } else {
                UserIdentity identity = identities.get(0);
                return this.getUserById(identity.getUserId());
            }
        } else {
            return null;
        }
    }

    @Override
    public UserIdentity addUserIdentity(Long id, String openid, String platformId) {
        return null;
    }
}
