package fre.shown.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Radon Freedom
 * created at 2019.04.02 上午11:14
 */

@RestController
public class ResourceController {

    @GetMapping("/resource")
    public String resource() {
        return "resource";
    }
}
