package top.parak.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import top.parak.handler.MyAuthenticationAccessDeniedHandler;
import top.parak.handler.MyAuthenticationFailureHandler;
import top.parak.handler.MyAuthenticationSucessHandler;

import javax.sql.DataSource;

/**
 * @author KHighness
 * @since 2020-12-21
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private MyAuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    @Qualifier("customUserDetailService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // 认证配置
            .formLogin()                                  // 设置表单登录
            .loginPage("/authentication")                 // 设置登录页面
            .loginProcessingUrl("/login")                 // 处理表单登录
            .usernameParameter("username")                // 用户名输入框的name
            .passwordParameter("password")                // 密码输入框的name
            // 结果处理
            .successHandler(authenticationSucessHandler)  // 登录成功处理
            .failureHandler(authenticationFailureHandler) // 登录失败处理
            .and()
             // 授权配置
            .authorizeRequests()
            .antMatchers("/authentication", "/register", "/add")  .permitAll() // 不需要认证即可访问
            .anyRequest().authenticated()                             // 所有请求都需要认证
            .and()
            // 记住我设置
            .rememberMe()
            .tokenValiditySeconds(3600)                    // 过期时间，单位秒
            .tokenRepository(persistentTokenRepository())  // token持久化仓库
            .userDetailsService(userDetailsService)        // 处理自动登录逻辑
            .and()
            // 权限配置
            .exceptionHandling()
            .accessDeniedHandler(authenticationAccessDeniedHandler) // 权限不足处理
            .and()
            // 关闭跨站CSRF保护
            .csrf().disable();
    }
}
