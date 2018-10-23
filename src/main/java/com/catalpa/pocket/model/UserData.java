package com.catalpa.pocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Data
public class UserData {
    private Long id;
    private String username;
    private String password;
    private String nickName;
    private Integer gender;
    private String headImgUrl;
    private String salt;

    /**
     * 密码盐.
     * @return
     */
    @JsonIgnore
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }
}
