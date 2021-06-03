package top.parak.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.parak.handler.MyAuthenticationFailureHandler;
import top.parak.handler.MyAuthenticationSuccessHandler;
import top.parak.validate.emailcode.EmailCodeAuthenticationConfig;
import top.parak.validate.emailcode.EmailCodeAuthenticationProvider;
import top.parak.validate.emailcode.EmailCodeFilter;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 资源服务器配置
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private EmailCodeFilter emailCodeFilter;
    @Autowired
    private EmailCodeAuthenticationConfig emailCodeAuthenticationConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(emailCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加邮件验证码过滤器
                .formLogin()                  // 表单登录
                .loginProcessingUrl("/login") // 处理表单登录URL
                .successHandler(authenticationSuccessHandler) // 处理登录成功
                .failureHandler(authenticationFailureHandler) // 处理登录失败
        .and()
                .authorizeRequests()                                // 授权配置
                .antMatchers("/code/email").permitAll() // 验证码接口无需认证
                .anyRequest().authenticated()                       // 所有接口都需要认证
        .and()
                .csrf().disable() // 关闭CSRF保护
        .apply(emailCodeAuthenticationConfig);
    }
}
