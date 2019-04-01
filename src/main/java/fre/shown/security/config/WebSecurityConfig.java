package fre.shown.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Radon Freedom
 * created at 2019.03.25 下午7:27
 */

@EnableWebSecurity(debug = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {

                return encodedPassword != null && encodedPassword.equals(rawPassword.toString());
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/test")
                .hasAuthority("ROLE_USER")

                .and()
                //设置自定义登录URL为/login
                //当未授权用户对被保护资源进行访问, 将redirect到下面的URI
                .formLogin().loginPage("/login")
                //设置包含登录信息的POST请求的URI为/login
                //从Filter层面看是UsernamePasswordAuthenticationFilter拦截POST:/login
                .loginProcessingUrl("/login")
                //用户名在请求中的属性名
                //调用Servlet层面的HttpServletRequest.getParameter(String)来获取结果
                .usernameParameter("email")
                //密码的属性名
                .passwordParameter("password")
                //认证成功或失败之后, 除了forward到别的路径, 也支持redirect到别的路径
                .successForwardUrl("/loginSuccess")
                .failureForwardUrl("/loginError")

                .and()
                .csrf().disable();
    }
}
