package com.catalpa.pocket.error;

import lombok.Data;

/**
 * Created by wanchuan01 on 2018/10/25.
 */
@Data
public class Error {
    private String httpCode;
    private String errorCode;
    private String errorDescription;
}
