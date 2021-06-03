package top.parak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import top.parak.enhancer.JWTokenEnhancer;

/**
 * @author KHighness
 * @since 2021-06-03
 */
@Configuration
public class JWTokenConfig {
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("parak.top"); // 签名密钥
        return accessTokenConverter;
    }

    @Bean
    public JWTokenEnhancer jwTokenEnhancer() {
        return new JWTokenEnhancer();
    }
}
