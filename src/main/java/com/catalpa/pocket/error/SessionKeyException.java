package com.catalpa.pocket.error;

import org.springframework.http.HttpStatus;

/**
 * <p>
 *
 * </p>
 *
 * @author bruce_wan
 * @since 2018/11/19
 */
public class SessionKeyException extends ApplicationException {

    private static final long serialVersionUID = 5620329981299896494L;

    private static final String HTTP_CODE = HttpStatus.BAD_REQUEST.toString();

    public SessionKeyException(String errorCode, String message) {
        super(errorCode, message);
        super.httpCode = HTTP_CODE;
    }

    public SessionKeyException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
        super.httpCode = HTTP_CODE;
    }

    public SessionKeyException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        super.httpCode = HTTP_CODE;
    }

    public SessionKeyException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode, message, cause, enableSuppression, writableStackTrace);
        super.httpCode = HTTP_CODE;
    }
}
