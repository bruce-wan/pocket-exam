package com.catalpa.pocket.controller;

import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.model.LoginRequest;
import com.catalpa.pocket.model.LoginResponse;
import com.catalpa.pocket.service.PlatformService;
import com.catalpa.pocket.service.SocialService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanchuan01 on 2018/10/23.
 */
@Log4j2
@RestController
@RequestMapping("/api/social")
public class SocialController {

    private final SocialService socialService;

    @Autowired
    public SocialController(@Qualifier("socialServiceDispatcher") SocialService socialService) {
        this.socialService = socialService;
    }

    @PostMapping
    public LoginResponse login(@RequestAttribute Platform platform, @RequestBody LoginRequest loginRequest) {
        if (log.isDebugEnabled()) {
            log.debug("platform: ====> "+ platform);
        }
        return socialService.login(platform, loginRequest);
    }
}
