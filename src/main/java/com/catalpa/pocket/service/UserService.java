package com.catalpa.pocket.service;

import com.catalpa.pocket.entity.UserIdentity;
import com.catalpa.pocket.model.UserData;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
public interface UserService {

    UserData createUser(String platformId, UserData userData);

    UserData getUserById(Long userId);

    UserData getUserByUnionId(String unionid);

    UserIdentity addUserIdentity(Long id, String openid, String platformId);
}
