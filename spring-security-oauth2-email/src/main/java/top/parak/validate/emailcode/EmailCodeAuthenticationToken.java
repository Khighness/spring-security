package top.parak.validate.emailcode;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 封装邮箱Token
 */
public class EmailCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -8522249673088578015L;

    private final Object principal;

    public EmailCodeAuthenticationToken(String email) {
        super(null);
        this.principal = email;
        setAuthenticated(false);
    }

    public EmailCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token trusted - use constructor which takes a GrantedAuthority list instead"
            );
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
