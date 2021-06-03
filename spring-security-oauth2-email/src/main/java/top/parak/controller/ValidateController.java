package top.parak.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import top.parak.service.EmailCodeService;
import top.parak.service.RedisCodeService;
import top.parak.validate.emailcode.EmailCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote REST发送邮件
 */
@Slf4j
@RestController
public class ValidateController {
    @Autowired
    private RedisCodeService redisCodeService;
    @Autowired
    private EmailCodeService emailCodeService;

    @GetMapping("/code/email")
    public void sendEmailCode(HttpServletRequest request, String email) throws Exception {
        String code = RandomStringUtils.randomNumeric(6);
        EmailCode emailCode = new EmailCode(code);
        ServletWebRequest webRequest = new ServletWebRequest(request);
        emailCodeService.send(webRequest, code, email);
        redisCodeService.save(emailCode, webRequest, email);
        log.info("用户使用邮箱[{}]登录，验证码为：[{}]", email, code);
    }
}
