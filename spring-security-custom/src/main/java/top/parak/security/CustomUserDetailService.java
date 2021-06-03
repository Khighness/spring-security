package top.parak.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.parak.entity.MyRole;
import top.parak.entity.MyUser;
import top.parak.entity.UserToRole;
import top.parak.mapper.RoleMapper;
import top.parak.mapper.UserMapper;
import top.parak.mapper.UserToRoleMapper;

import javax.annotation.Resource;

/**
 * @author KHighness
 * @since 2020-12-21
 */
@Slf4j
@Configuration
public class CustomUserDetailService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserToRoleMapper userToRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username != null) {
            log.info("登录用户名 => [{}]", username);
        }
        // 查询用户密码
        QueryWrapper<MyUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        MyUser user = userMapper.selectOne(userQueryWrapper);
        // 查询角色ID
        QueryWrapper<UserToRole> userToRoleQueryWrapper = new QueryWrapper<>();
        userToRoleQueryWrapper.eq("user_id", user.getId());
        UserToRole userToRole = userToRoleMapper.selectOne(userToRoleQueryWrapper);
        int roleId = userToRole.getRoleId();
        // 查询用户角色
        QueryWrapper<MyRole> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("id", roleId);
        MyRole role = roleMapper.selectOne(roleQueryWrapper);
        // 返回认证信息
        return new User(username, user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList(role.getRoleName()));
    }
}
