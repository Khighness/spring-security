package top.parak.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 用户名认证逻辑
 */
@Configuration
public class NameUserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟数据库查询逻辑；根据用户名查找用户
        String password = passwordEncoder.encode("KAG1823");
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // Khighness为管理员，其他都为访客
        if (StringUtils.equalsIgnoreCase("Khighness", username)) {
            authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        } else {
            authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("guest");
        }
        return new User(username, password, authorityList);
    }
}
