package com.catalpa.pocket.service;

import com.catalpa.pocket.model.UserData;

import java.io.UnsupportedEncodingException;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
public interface UserService {

    UserData createUser(UserData userData) throws UnsupportedEncodingException;
}
