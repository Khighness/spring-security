package top.parak.validate.emailcode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import top.parak.service.RedisCodeService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 验证码校验过滤器
 */
@Component
public class EmailCodeFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RedisCodeService redisCodeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equalsIgnoreCase("/login/email", request.getRequestURI()) && StringUtils.equalsIgnoreCase("post", request.getMethod())) {
            try {
                validateCode(new ServletWebRequest(request));
            } catch (Exception e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, new AuthenticationServiceException(e.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(ServletWebRequest request) throws Exception {
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "code");
        String emailInRequest = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "email");
        if (StringUtils.isBlank(codeInRequest))
            throw new Exception("The verification code cannot be null");
        String codeInRedis = redisCodeService.get(request, emailInRequest);
        if (codeInRedis == null)
            throw new Exception("No verification code was found for email " + emailInRequest);
        if (!StringUtils.equals(codeInRequest, codeInRedis))
            throw new Exception("The verification code is wrong");
        redisCodeService.remove(request, emailInRequest);
    }
}
