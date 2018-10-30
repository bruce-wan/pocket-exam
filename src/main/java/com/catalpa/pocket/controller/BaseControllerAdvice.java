package com.catalpa.pocket.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.catalpa.pocket.error.Error;
import com.catalpa.pocket.error.ApplicationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanchuan01 on 2018/10/25.
 */
@Log4j2
@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void processException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("Catch Exception", ex);
        Map payload = new HashMap();
        processError("500", "5001", ex.getMessage(), response);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void processRuntimeException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("Catch RuntimeException", ex);
        processError("500", "5002", ex.getMessage(), response);
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void processApplicationException(ApplicationException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("Catch RuntimeException", ex);
        processError("500", "5003", ex.getMessage(), response);
    }

    private void processError(String httpCode, String errorCode, String description, HttpServletResponse httpResponse) {
        Error error = new Error();
        error.setHttpCode(httpCode);
        error.setErrorCode(errorCode);
        error.setErrorDescription(description);
        try {
            httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpResponse.getWriter().write(JSONUtils.toJSONString(error));
        } catch (IOException e) {
            log.error("Error writing to the response", e);
        }
    }
}
