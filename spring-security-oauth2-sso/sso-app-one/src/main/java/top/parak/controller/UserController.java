package top.parak.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author KHighness
 * @since 2021-06-03
 * @apiNote 用户REST接口
 */
@RestController
public class UserController {

    @GetMapping("/principal")
    public Principal principal(Principal principal) {
        return principal;
    }

    @GetMapping("/write")
    @PreAuthorize("hasAuthority('admin')")
    public String write() {
        return "您拥有write权限！";
    }

    @GetMapping("/read")
    @PreAuthorize("hasAuthority('guest')")
    public String read() {
        return "您拥有read权限！";
    }
}
