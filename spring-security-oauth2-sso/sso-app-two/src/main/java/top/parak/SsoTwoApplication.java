package top.parak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @author KHighness
 * @since 2021-06-03
 * @apiNote 客户端2
 */
@EnableOAuth2Sso
@SpringBootApplication
public class SsoTwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoTwoApplication.class, args);
    }
}
