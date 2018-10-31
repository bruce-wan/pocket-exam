package com.catalpa.pocket.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * Created by bruce on 2018/7/10.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 6328112357011169818L;

    protected String httpCode = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    protected String errorCode;

    public ApplicationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ApplicationException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ApplicationException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
