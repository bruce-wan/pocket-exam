package com.catalpa.pocket.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by wanchuan01 on 2018/10/22.
 */
@Log4j2
@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String indexPage() {
        return "index";
    }
}
