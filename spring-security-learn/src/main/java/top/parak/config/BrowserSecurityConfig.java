package top.parak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author KHighness
 * @since 2020-12-21
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin()       // 表单认证
                .and()
                .authorizeRequests()  // 授权方式
                .anyRequest()         // 所有请求
                .authenticated();     // 都需认证
    }
}
