package top.parak.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote REST资源接口
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public Object index(@AuthenticationPrincipal Authentication authentication) {
        return authentication;
    }
}
