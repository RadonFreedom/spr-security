package fre.shown.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Radon Freedom
 * created at 2019.03.25 下午7:22
 */


@RestController
public class UserController {

    @PostMapping("")
    public void login(String name, String password){
    }
}
