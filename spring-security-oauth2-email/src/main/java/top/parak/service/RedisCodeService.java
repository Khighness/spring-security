package top.parak.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import top.parak.validate.emailcode.EmailCode;

import java.util.concurrent.TimeUnit;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 缓存验证码操作
 */
@Service
public class RedisCodeService {
    /** 邮箱验证码key的前缀 */
    private final static String EMAIL_CODE_PREFIX = "EMAIL_CODE:";
    /** 邮箱验证码过期时间S */
    private final static Integer EMAIL_CODE_TIMEOUT = 300;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 保存验证码
     */
    public void save(EmailCode emailCode, ServletWebRequest request, String email) throws Exception {
        redisTemplate.opsForValue().set(key(request, email), emailCode.getCode(), EMAIL_CODE_TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 获取验证码
     */
    public String get(ServletWebRequest request, String email) throws Exception{
        String key = key(request, email);
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(key);
        if (expire == -2) // 过期
            throw new Exception("The verification code has expired");
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 移除验证码
     */
    public void remove(ServletWebRequest request, String email) throws Exception {
        redisTemplate.delete(key(request, email));
    }

    private String key(ServletWebRequest request, String email) throws Exception {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new Exception("No device id was found in request header");
        }
        return EMAIL_CODE_PREFIX + deviceId + ":" + email;
    }
}
