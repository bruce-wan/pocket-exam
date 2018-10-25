package com.catalpa.pocket.model;

import lombok.Data;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Data
public class LoginRequest {

    /*mini program arguments*/
    private String code;
    private String encryptedData;
    private String iv;
    /*mini program arguments*/

}
