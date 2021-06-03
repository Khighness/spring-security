package top.parak.controller;

import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote REST资源接口
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public Object index(@AuthenticationPrincipal Authentication authentication, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(authorization, "bearer");
        return Jwts.parser().setSigningKey("parak.top".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
    }
}
