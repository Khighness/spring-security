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
 * @since 2021-06-03
 * @apiNote 自定义认证
 */
@Configuration
public class UserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("KAG1823");
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (StringUtils.equalsIgnoreCase("Khighness", username)) {
            authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        } else {
            authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("guest");
        }
        return new User(username, password, authorityList);
    }
}
