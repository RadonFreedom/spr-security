package fre.shown.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    public void configure(WebSecurity web) {
        // Spring Security should completely ignore URLs starting with /static/
        web
                .ignoring()
                .mvcMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/test")
                .hasAuthority("ROLE_USER")

                .and()
                .formLogin().loginPage("/login.html")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successForwardUrl("/loginSuccess")
                .failureForwardUrl("/loginError")

                .and()
                .csrf().disable();
    }
}
