package top.parak.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.parak.entity.MyUser;
import top.parak.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * @author KHighness
 * @since 2020-12-22
 */
@Slf4j
@Controller
public class RegisterController {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @ResponseBody
    @PostMapping("/add")
    public String saveInfo(MyUser user) {
        log.info("注册用户名 => [{}]", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.insert(user) == 1 ? "注册成功" : "注册失败";
    }

}
