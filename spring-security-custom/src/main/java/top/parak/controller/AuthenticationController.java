package top.parak.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

/**
 * @author KHighness
 * @since 2020-12-21
 */
@Controller
public class AuthenticationController {

    /**
     * 登录认证
     */
    @GetMapping("/authentication")
    public String authenticationLogin() {
        return "login";
    }

    /**
     * 打招呼接口
     */
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "Hello Khighness";
    }

    /**
     * 登录成功返回认证信息
     */
    @ResponseBody
    @GetMapping("/success")
    public Object success(Authentication authentication) {
        return authentication;
    }

}
