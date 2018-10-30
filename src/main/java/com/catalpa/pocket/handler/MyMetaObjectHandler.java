package com.catalpa.pocket.handler;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Timestamp;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Log4j2
public class MyMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("新增的时候干点不可描述的事情");
        //sample usage
        Object testType = this.getFieldValByName("testType", metaObject);
        System.out.println("testType=" + testType);
        if (testType == null) {
            //测试实体没有的字段，配置在公共填充，不应该set到实体里面
            this.setFieldValByName("testType1", 3, metaObject);
            this.setFieldValByName("testType", 3, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("更新的时候干点不可描述的事情");
        //sample usage
        //测试实体没有的字段，配置在公共填充，不应该set到实体里面
        this.setFieldValByName("lastUpdatedDt1", new Timestamp(System.currentTimeMillis()), metaObject);
        this.setFieldValByName("lastUpdatedDt", new Timestamp(System.currentTimeMillis()), metaObject);
    }
}
