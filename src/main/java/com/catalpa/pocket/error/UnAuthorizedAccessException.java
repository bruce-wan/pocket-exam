package com.catalpa.pocket.error;

/**
 * Created by bruce on 2018/7/10.
 */
public class UnAuthorizedAccessException extends ApplicationException {

    private static final long serialVersionUID = 5620329981299896494L;
    private static final String HTTP_CODE = "401";

    public UnAuthorizedAccessException(String errorCode) {
        super(HTTP_CODE, errorCode);
    }

    public UnAuthorizedAccessException(String errorCode, String message) {
        super(HTTP_CODE, errorCode, message);
    }

    public UnAuthorizedAccessException(String errorCode, String message, Throwable cause) {
        super(HTTP_CODE, errorCode, message, cause);
    }

    public UnAuthorizedAccessException(String errorCode, Throwable cause) {
        super(HTTP_CODE, errorCode, cause);
    }

    public UnAuthorizedAccessException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(HTTP_CODE, errorCode, message, cause, enableSuppression, writableStackTrace);
    }
}
