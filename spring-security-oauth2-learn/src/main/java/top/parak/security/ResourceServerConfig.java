package top.parak.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import top.parak.handler.MyAuthenticationFailureHandler;
import top.parak.handler.MyAuthenticationSuccessHandler;

/**
 * @author KHighness
 * @since 2021-06-02
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()                  // 表单登录
            .loginProcessingUrl("/login") // 处理表单登录URL
            .successHandler(authenticationSuccessHandler) // 处理登录成功
            .failureHandler(authenticationFailureHandler) // 处理登录失败
        .and()
            .authorizeRequests() // 授权配置
            .anyRequest()        // 所有请求
            .authenticated()     // 都需认证
        .and()
            .csrf().disable()    // 禁用CSRF保护
        ;
    }
}
