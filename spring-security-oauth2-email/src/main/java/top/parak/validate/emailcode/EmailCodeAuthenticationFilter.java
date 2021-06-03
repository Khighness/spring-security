package top.parak.validate.emailcode;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author KHighness
 * @since 2021-06-02
 */
public class EmailCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String EMAIL_KEY = "email";
    private String emailParameter = EMAIL_KEY;
    private boolean postOnly = true;

    protected EmailCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/email", "POST"));
    }

    public void setEmailParameter(String emailParameter) {
        this.emailParameter = emailParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getEmailParameter() {
        return emailParameter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST"))
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        String email = request.getParameter(emailParameter);
        if (email == null) email = "";
        email = email.trim();
        EmailCodeAuthenticationToken token = new EmailCodeAuthenticationToken(email);
        token.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }
}
