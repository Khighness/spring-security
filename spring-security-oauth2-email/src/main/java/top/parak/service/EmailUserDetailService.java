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
import org.springframework.stereotype.Service;
import top.parak.entity.MyUser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 邮箱认证逻辑
 */
@Configuration
public class EmailUserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 模拟数据库查询逻辑；根据邮箱查找用户
        MyUser user = new MyUser();
        user.setUserName(email);
        user.setPassword(passwordEncoder.encode("KAG1823"));
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // 只有邮箱为parakovo@gmail的用户才为管理员admin，其他用户都为访客guest
        if (StringUtils.equalsIgnoreCase("parakovo@gmail.com", email)) {
            authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        } else {
            authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("guest");
        }
        return new User(email, user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), authorityList);
    }
}
