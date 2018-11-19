package com.catalpa.pocket.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.catalpa.pocket.config.Constants;
import com.catalpa.pocket.config.ShiroProperties;
import com.catalpa.pocket.entity.*;
import com.catalpa.pocket.error.ApplicationException;
import com.catalpa.pocket.error.ResourceNotFoundException;
import com.catalpa.pocket.mapper.ExamPaperMapper;
import com.catalpa.pocket.mapper.UserIdentityMapper;
import com.catalpa.pocket.mapper.UserInfoMapper;
import com.catalpa.pocket.mapper.UserSessionMapper;
import com.catalpa.pocket.model.ExamData;
import com.catalpa.pocket.model.QuestionData;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final UserSessionMapper userSessionMapper;
    private final ExamPaperMapper examPaperMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public UserServiceImpl(ShiroProperties shiroProperties, UserInfoMapper userInfoMapper, UserIdentityMapper userIdentityMapper, UserSessionMapper userSessionMapper, ExamPaperMapper examPaperMapper, RedisTemplate<String, String> redisTemplate) {
        this.shiroProperties = shiroProperties;
        this.userInfoMapper = userInfoMapper;
        this.userIdentityMapper = userIdentityMapper;
        this.userSessionMapper = userSessionMapper;
        this.examPaperMapper = examPaperMapper;
        this.redisTemplate = redisTemplate;

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
            throw new ApplicationException("50002", message);
        }

        String salt = RandomUtil.generateString(false, true, true, null, 10);
        String password = StringUtils.isNotBlank(userData.getPassword()) ? userData.getPassword() : "123456";
        SimpleHash simpleHash = new SimpleHash(shiroProperties.getHashAlgorithmName(), password, ByteSource.Util.bytes(username + salt), shiroProperties.getHashIterations());

        userInfo.setPassword(simpleHash.toString());
        userInfo.setNickName(userData.getNickName());
        userInfo.setGender(userData.getGender());
        userInfo.setCity(userData.getCity());
        userInfo.setProvince(userData.getProvince());
        userInfo.setCountry(userData.getCountry());
        userInfo.setHeadImgUrl(userData.getHeadImgUrl());
        userInfo.setSalt(salt);
        try {
            userInfoMapper.insert(userInfo);
        } catch (Exception e) {
            String message = String.format("can create new user with data: %s", userData);
            log.error(message);
            throw new ApplicationException("50002", message, e);
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
            userData.setCity(userInfo.getCity());
            userData.setProvince(userInfo.getProvince());
            userData.setCountry(userInfo.getCountry());
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
                throw new ApplicationException("50003", message);
            } else {
                UserIdentity identity = identities.get(0);
                return this.getUserById(identity.getUserId());
            }
        } else {
            return null;
        }
    }

    @Override
    public UserIdentity addUserIdentity(Long userId, String thirdPartyId, String platformId) {
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(userId);
        userIdentity.setThirdPartyId(thirdPartyId);
        userIdentity.setPlatformId(platformId);

        userIdentityMapper.insert(userIdentity);
        return userIdentity;
    }

    @Override
    public UserSession saveOrUpdateUserSession(Long userId, String skey, String sessionKeyJson) {

        UserSession params = new UserSession();
        params.setUserId(userId);

        UserSession userSession = userSessionMapper.selectOne(params);
        if (userSession == null) {
            userSession = new UserSession();
            userSession.setUserId(userId);
            userSession.setSkey(skey);
            userSession.setSessionKey(sessionKeyJson);
            userSessionMapper.insert(userSession);
            redisTemplate.opsForValue().set(Constants.MINIPROGRAM_SESSIONKEY + skey, sessionKeyJson);
        } else if (!userSession.getSkey().equals(skey)){
            redisTemplate.delete(Constants.MINIPROGRAM_SESSIONKEY + userSession.getSkey());
            userSession.setSkey(skey);
            userSession.setSessionKey(sessionKeyJson);
            userSessionMapper.updateById(userSession);
            redisTemplate.opsForValue().set(Constants.MINIPROGRAM_SESSIONKEY + skey, sessionKeyJson);
        }
        return userSession;
    }

    @Override
    public ExamData addUserExams(Platform platform, Long userId, ExamData examData) {

        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (userInfo == null) {
            String message = "user has not found with userid is " + userId;
            log.error(message);
            throw new ResourceNotFoundException("40001", message);
        }

        ExamPaper examPaper = new ExamPaper();

        examPaper.setUserId(userId);
        examPaper.setName(examData.getName());
        examPaper.setCatalog(examData.getCatalog());
        examPaper.setLevel(examData.getLevel());
        examPaper.setTotalScore(examData.getTotalScore());
        examPaper.setPassScore(examData.getPassScore());
        examPaper.setUserScore(examData.getUserScore());
        examPaper.setScoreGrade(examData.getScoreGrade());
        examPaper.setStartTime(examData.getStartTime());
        examPaper.setEndTime(examData.getEndTime());
        examPaper.setDuration(examData.getDuration());
        examPaper.setContent(JSONObject.toJSONString(examData.getQuestionDatas()));
        examPaper.setRemark(examData.getRemark());

        examPaperMapper.insert(examPaper);
        examData.setId(examPaper.getId());

        return examData;
    }

    @Override
    public Page<ExamData> getUserExams(Page<ExamData> page, Platform platform, Long userId, Integer catalog, Integer level, Integer days) {
        EntityWrapper<ExamPaper> wrapper = new EntityWrapper<>();
        ExamPaper examPaper = new ExamPaper();
        examPaper.setUserId(userId);
        examPaper.setCatalog(catalog);
        examPaper.setLevel(level);
        wrapper.setEntity(examPaper);

        wrapper.and().ge("start_time", Date.from(ZonedDateTime.now().minusDays(days).toInstant()));

        List<String> columns = new ArrayList<>();
        columns.add("start_time");
        wrapper.orderDesc(columns);

        List<ExamPaper> examPaperList = examPaperMapper.selectPage(page, wrapper);

        List<ExamData> records = examPaperList.stream().map(paper -> {
            ExamData examData = new ExamData();

            examData.setId(paper.getId());
            examData.setUserId(paper.getUserId());
            examData.setName(paper.getName());
            examData.setCatalog(paper.getCatalog());
            examData.setLevel(paper.getLevel());
            examData.setTotalScore(paper.getTotalScore());
            examData.setPassScore(paper.getPassScore());
            examData.setUserScore(paper.getUserScore());
            examData.setScoreGrade(paper.getScoreGrade());
            examData.setStartTime(paper.getStartTime());
            examData.setEndTime(paper.getEndTime());
            examData.setDuration(paper.getDuration());
            examData.setRemark(paper.getRemark());
            List<QuestionData> questionDatas = JSONArray.parseArray(paper.getContent(), QuestionData.class);
            examData.setQuestionDatas(questionDatas);

            return examData;
        }).collect(Collectors.toList());
        page.setRecords(records);

        return page;
    }

}
