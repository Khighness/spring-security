package top.parak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KHighness
 * @since 2020-12-21
 */
@RestController
public class HelloController {

    @GetMapping("/{name}")
    public String sayHello(@PathVariable(value = "name") String name) {
        return "Hello " + name + "!";
    }
}
