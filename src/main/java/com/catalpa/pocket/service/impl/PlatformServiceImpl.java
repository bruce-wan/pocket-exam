package com.catalpa.pocket.service.impl;

import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.mapper.PlatformMapper;
import com.catalpa.pocket.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wanchuan01 on 2018/10/23
 */
@Service
public class PlatformServiceImpl implements PlatformService {

    private final PlatformMapper platformMapper;

    @Autowired
    public PlatformServiceImpl(PlatformMapper platformMapper) {
        this.platformMapper = platformMapper;
    }

    @Override
    public Platform getPlatformById(String platformId) {
        return platformMapper.selectById(platformId);
    }
}
