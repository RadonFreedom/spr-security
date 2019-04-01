package fre.shown.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Radon Freedom
 * created at 2019.03.25 下午7:22
 */


@RestController
public class LoginController {


    @RequestMapping("/loginError")
    public Boolean loginError() {
        return false;
    }

    @RequestMapping("/loginSuccess")
    public Boolean loginSuccess() {
        return true;
    }
}
