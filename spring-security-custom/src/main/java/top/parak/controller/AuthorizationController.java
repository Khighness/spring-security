package top.parak.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KHighness
 * @since 2021-06-01
 */
@RestController
public class AuthorizationController {

    @GetMapping("/auth/manage")
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public String manage() {
        return "您是Admin或者manager，可以访问";
    }

    @GetMapping("/auth/normal")
    @PreAuthorize("hasAuthority('normal')")
    public String normal() {
        return "您是普通角色，可以访问";
    }
}
