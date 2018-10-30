package com.catalpa.pocket.config;

import com.catalpa.pocket.handler.DefaultRestResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wanchuan01 on 2018/10/25.
 */
@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(60000);//ms
        factory.setConnectTimeout(60000);//ms

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setErrorHandler(new DefaultRestResponseErrorHandler());
        return restTemplate;
    }
}
