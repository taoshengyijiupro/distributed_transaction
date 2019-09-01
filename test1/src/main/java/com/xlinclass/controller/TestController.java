package com.xlinclass.controller;

import com.xlinclass.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/26 14:10
 */

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "server1/test")
    public void test() {
        testService.test();
    }
}
