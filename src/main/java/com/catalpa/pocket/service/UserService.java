package com.catalpa.pocket.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.entity.UserIdentity;
import com.catalpa.pocket.entity.UserSession;
import com.catalpa.pocket.model.ExamData;
import com.catalpa.pocket.model.UserData;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
public interface UserService {

    UserData createUser(String platformId, UserData userData);

    UserData getUserById(Long userId);

    UserData getUserByThirdPartyId(String platformId, String thirdPartyId);

    UserIdentity addUserIdentity(Long userId, String thirdPartyId, String platformId);

    UserSession saveOrUpdateUserSession(Long userId, String skey, String sessionKeyJson);

    ExamData addUserExams(Platform platform, Long userId, ExamData examData);

    Page<ExamData> getUserExams(Page<ExamData> page, Platform platform, Long userId, Integer catalog, Integer level, Integer days);

}
