package top.parak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 发送邮件服务
 */
@Service
public class EmailCodeService {
    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private RedisCodeService redisCodeService;

    /**
     * 发送验证码到指定邮箱
     * @param request 请求
     * @param code    验证码
     * @param toMail  目标邮箱
     */
    public void send(ServletWebRequest request, String code, String toMail) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(toMail);
        message.setSubject("SpringSecurity验证码");
        message.setText("您正在登录Spring-Security-OAuth2, 验证码为：" + code);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            redisCodeService.remove(request, toMail);
            throw new Exception(e.getMessage());
        }
    }
}
