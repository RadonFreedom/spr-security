package fre.shown.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Radon Freedom
 * created at 2019.03.25 下午7:22
 */


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @ResponseBody
    @RequestMapping("/loginError")
    public Boolean loginError() {
        return false;
    }

    @ResponseBody
    @RequestMapping("/loginSuccess")
    public Boolean loginSuccess() {
        return true;
    }
}
