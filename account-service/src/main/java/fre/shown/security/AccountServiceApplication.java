package fre.shown.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Radon Freedom
 * created at 2019.04.02 上午8:43
 */

@EnableOAuth2Client
@SpringBootApplication
public class AccountServiceApplication {

    @ResponseBody
    @GetMapping("/test")
    public void test() {

    }

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}
