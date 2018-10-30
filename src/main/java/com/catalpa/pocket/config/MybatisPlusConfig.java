package com.catalpa.pocket.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Configuration
@MapperScan(value = {"com.catalpa.pocket.mapper"})
@EnableTransactionManagement
public class MybatisPlusConfig {
    @Bean
    public PerformanceInterceptor performanceInterceptor()
    {
        return new PerformanceInterceptor();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor()
    {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}
