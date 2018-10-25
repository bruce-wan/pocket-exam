package com.catalpa.pocket.model;

import com.catalpa.pocket.entity.UserIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Data
public class UserData {
    private Long id;
    private String username;
    private String password = "123456";
    private String nickName;
    private Integer gender;
    private Integer sex;
    private String headImgUrl;
    private String avatarUrl;
    private String salt;
    private String openId;
    private String unionId;

    private Integer subscribe;
    private Timestamp subscribeTime;
    private String subscribeScene;
    private Integer qrScene;
    private String qrSceneStr;
    private Integer groupid;
    private List<Integer> tagidList;
    private String language;
    private Date birthdate;
    private String city;
    private String province;
    private String country;
    private WaterMark waterMark;
    private String remark;

    private List<UserIdentity> userIdentities;

    @Data
    public class WaterMark
    {
        private Timestamp timestamp;
        private String appid;
    }
}
