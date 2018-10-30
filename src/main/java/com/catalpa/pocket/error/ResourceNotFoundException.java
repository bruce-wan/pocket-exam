package com.catalpa.pocket.error;

import org.springframework.http.HttpStatus;

/**
 * Created by bruce on 2018/7/10.
 */
public class ResourceNotFoundException extends ApplicationException {

    private static final long serialVersionUID = 5620329981299896494L;
    private static final String HTTP_CODE = HttpStatus.BAD_REQUEST.toString();

    public ResourceNotFoundException(String errorCode) {
        super(HTTP_CODE, errorCode);
    }

    public ResourceNotFoundException(String errorCode, String message) {
        super(HTTP_CODE, errorCode, message);
    }

    public ResourceNotFoundException(String errorCode, String message, Throwable cause) {
        super(HTTP_CODE, errorCode, message, cause);
    }

    public ResourceNotFoundException(String errorCode, Throwable cause) {
        super(HTTP_CODE, errorCode, cause);
    }

    public ResourceNotFoundException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(HTTP_CODE, errorCode, message, cause, enableSuppression, writableStackTrace);
    }
}
