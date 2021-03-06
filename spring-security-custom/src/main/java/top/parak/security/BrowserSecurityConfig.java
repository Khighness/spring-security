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
            // ????????????
            .formLogin()                                  // ??????????????????
            .loginPage("/authentication")                 // ??????????????????
            .loginProcessingUrl("/login")                 // ??????????????????
            .usernameParameter("username")                // ?????????????????????name
            .passwordParameter("password")                // ??????????????????name
            // ????????????
            .successHandler(authenticationSucessHandler)  // ??????????????????
            .failureHandler(authenticationFailureHandler) // ??????????????????
            .and()
             // ????????????
            .authorizeRequests()
            .antMatchers("/authentication", "/register", "/add")  .permitAll() // ???????????????????????????
            .anyRequest().authenticated()                             // ???????????????????????????
            .and()
            // ???????????????
            .rememberMe()
            .tokenValiditySeconds(3600)                    // ????????????????????????
            .tokenRepository(persistentTokenRepository())  // token???????????????
            .userDetailsService(userDetailsService)        // ????????????????????????
            .and()
            // ????????????
            .exceptionHandling()
            .accessDeniedHandler(authenticationAccessDeniedHandler) // ??????????????????
            .and()
            // ????????????CSRF??????
            .csrf().disable();
    }
}
