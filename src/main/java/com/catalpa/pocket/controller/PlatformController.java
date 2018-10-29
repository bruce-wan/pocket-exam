package com.catalpa.pocket.controller;


import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.error.ResourceNotFoundException;
import com.catalpa.pocket.service.PlatformService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-10-23
 */
@Log4j2
@RestController
@RequestMapping("/api/platform")
public class PlatformController {

    private final PlatformService platformService;

    @Autowired
    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @PutMapping("/{platformid}")
    public Platform update(@PathVariable String platformid, @RequestBody Platform platform) {

        Platform record = platformService.selectById(platformid);

        if (record == null) {
            String message = String.format("platform not found with platfromid is %s", platformid);
            log.error(message);
            throw new ResourceNotFoundException("40001", message);
        } else {
            platform.setPlatformId(platformid);
            platformService.updateById(platform);

            if (StringUtils.isNotBlank(platform.getPlatformSecret())) {
                record.setPlatformSecret(platform.getPlatformSecret());
            }if (StringUtils.isNotBlank(platform.getPlatformName())) {
                record.setPlatformName(platform.getPlatformName());
            } else if (StringUtils.isNotBlank(platform.getPlatformScope())) {
                record.setPlatformScope(platform.getPlatformScope());
            } else if (StringUtils.isNotBlank(platform.getPlatformName())) {
                record.setPlatformName(platform.getPlatformName());
            } else if (StringUtils.isNotBlank(platform.getPlatformIcon())) {
                record.setPlatformIcon(platform.getPlatformIcon());
            } else if (StringUtils.isNotBlank(platform.getGrantType())) {
                record.setGrantType(platform.getGrantType());
            }
        }

        return record;
    }
}

