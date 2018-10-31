package com.catalpa.pocket.error;

import org.springframework.http.HttpStatus;

/**
 * Created by bruce on 2018/7/10.
 */
public class UnAuthorizedAccessException extends ApplicationException {

    private static final long serialVersionUID = 5620329981299896494L;
    private static final String HTTP_CODE = HttpStatus.UNAUTHORIZED.toString();

    public UnAuthorizedAccessException(String errorCode, String message) {
        super(errorCode, message);
        super.httpCode = HTTP_CODE;
    }

    public UnAuthorizedAccessException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
        super.httpCode = HTTP_CODE;
    }

    public UnAuthorizedAccessException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        super.httpCode = HTTP_CODE;
    }

    public UnAuthorizedAccessException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, message, cause, enableSuppression, writableStackTrace);
        super.httpCode = HTTP_CODE;
    }
}
