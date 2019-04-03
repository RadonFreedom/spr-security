package fre.shown.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Radon Freedom
 * created at 2019.04.03 上午10:18
 */


@RestController
public class AuthorizationCodeController {

    @RequestMapping("/redirect")
    public String redirect(@RequestParam("code") String authorizationCode) {

        return authorizationCode;
    }
}
