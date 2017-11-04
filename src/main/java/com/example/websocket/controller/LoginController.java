package com.example.websocket.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuji
 * @date 2017/11/2 14:13
 */

@RestController
@RequestMapping(value = "/chat")
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    private static final String LOGGER_HEADER = "[LoginController服务端消息]";

    @PostMapping(value = "/login")
    public String login(@RequestBody String userName) {
        logger.info(LOGGER_HEADER + userName + "登陆成功");
        return userName;
    }
}
