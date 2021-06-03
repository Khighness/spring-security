package top.parak.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 处理登录成功
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Qualifier("defaultAuthorizationServerTokenServices")
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1. 从请求头获取clientId
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("No client information was found in the request header");
        }
        String[] tokens = this.extractAndDecodeHeader(header);
        String clientId = tokens[0], clientSecret = tokens[1];
        TokenRequest tokenRequest = null;
        // 2. 通过ClientDetailService获取ClientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        // 3. 校验ClientId和ClientSecret的正确性
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("No client details was found for clientId " + clientId);
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("The client secret is invalid");
        } else {
            // 4. 通过TokenRequest构造器生成TokenRequest
            tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");
        }
        // 5. 通过TokenRequest的createOAuth2Request方法获取OAuth2Request
        OAuth2Request auth2Request = tokenRequest.createOAuth2Request(clientDetails);
        // 6. 通过Authentication和OAuth2Request构造出OAuth2Authentication
        OAuth2Authentication auth2Authentication = new OAuth2Authentication(auth2Request, authentication);
        // 7. 通过AuthenticationSServerTokenService生成OAuth2AccessToken
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(auth2Authentication);
        // 8. 返回token
        log.info("[{}]登录成功", authentication.getName());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(token));
    }

    /**
     * 从header中获取client-id和client-secret
     */
    private String[] extractAndDecodeHeader(String header) {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        String token = new String(decoded, StandardCharsets.UTF_8);
        int index = token.indexOf(":");
        if (index == -1) {
            throw new BadCredentialsException("The basic authentication token is invalid");
        } else {
            return new String[]{token.substring(0, index), token.substring(index + 1)};
        }
    }
}
