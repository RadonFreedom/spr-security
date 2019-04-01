package fre.shown.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Radon Freedom
 * created at 2019.03.26 下午10:39
 */

@Configuration
public class ErrorConfig implements ErrorViewResolver {

    /**
     * 从容器的属性中寻找 server.error.path 属性值,
     * 如果找不到再寻找 error.path,
     * 如果两个都没有找到, 设置为/error
     */
    @Value("${server.error.path:${error.path:/error}}")
    private String serverErrorPath;

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {

        Map<String, Object> newModel = new HashMap<>(16);
        for (String key : model.keySet()) {
            newModel.put(key, model.get(key));
        }

        newModel.put("status", status.value());
        newModel.put("error", status.getReasonPhrase());

        if (request.getRequestURI().startsWith(serverErrorPath)) {
            newModel.put("status", "404");
            newModel.put("error", "Not Found");
        }

        return new ModelAndView("error", Collections.unmodifiableMap(newModel));
    }
}
