package com.catalpa.pocket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Data
@Component
@ConfigurationProperties(prefix = "shiro")
@PropertySource(value = {"classpath:shiro.properties"})
public class ShiroProperties {

    private String hashAlgorithmName;
    private int hashIterations;
}
