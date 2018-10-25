package com.catalpa.pocket.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by bruce on 2018/7/10.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 6328112357011169818L;
    private static final String HTTP_CODE = "500";

    protected String httpCode;
    protected String errorCode;

    public ApplicationException(String httpCode, String errorCode) {
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public ApplicationException(String httpCode, String errorCode, String message) {
        super(message);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public ApplicationException(String httpCode, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public ApplicationException(String httpCode, String errorCode, Throwable cause) {
        super(cause);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public ApplicationException(String httpCode, String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }
}
