package top.parak.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BrowserSecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoder() {
        for (int i = 1; i <= 5; i++) {
            log.info("第{}次加密 => [{}]", i, passwordEncoder.encode("KAG1823"));
        }
    }

    @Test
    void test() {
        System.out.println(passwordEncoder.upgradeEncoding("$2a$10$y73FFakBgkHMCajGqJplee/dd/CHkq9K/GSrlvZ822PoI7QXPFaUu"));
    }

}
