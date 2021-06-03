package top.parak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote Server Starter
 */
@EnableAsync
@SpringBootApplication
public class SpringSecurityOAuth2EmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOAuth2EmailApplication.class, args);
    }
}
