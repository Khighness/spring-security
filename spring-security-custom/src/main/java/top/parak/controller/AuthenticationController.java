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
     * <p>登录认证</p>
     * @return
     * @throws IOException
     */
    @GetMapping("/authentication")
    public String authenticationLogin() {
        return "login";
    }

    /**
     * <p>打招呼接口</p>
     * @return
     */
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "Hello Khighness";
    }

    /**
     * <p>登录成功返回认证信息</p>
     * @param authentication
     * @return
     */
    @ResponseBody
    @GetMapping("/success")
    public Object success(Authentication authentication) {
        return authentication;
    }

}
