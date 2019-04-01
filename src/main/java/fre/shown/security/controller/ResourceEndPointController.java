package fre.shown.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Radon Freedom
 * created at 2019.04.01 上午10:27
 */

@RestController
public class ResourceEndPointController {

    @GetMapping("/test")
    public String test() {

        return "您已被授权访问敏感资源!";
    }
}
