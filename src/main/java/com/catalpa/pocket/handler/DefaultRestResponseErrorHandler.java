package com.catalpa.pocket.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * Created by wanchuan01 on 2018/10/25.
 */
@Log4j2
public class DefaultRestResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.warn(response.getStatusCode().toString());
    }
}
