# v1.0 简单SSO实现

> 为了让 [tryboot](https://github.com/RadonFreedom) 项目更好实现认证功能, 希望能集成 Spring Security.

v1.0版本使用Spring Security实现了简单的SSO.



## endpoints

可以尝试访问下面的节点:

| Method |  Path  |    Description     | Authority Needed | Available from UI |
| :----: | :----: | :----------------: | :--------------: | :---------------: |
|  GET   |   /    |    Welcome page    |       (X)        |         Y         |
|  GET   | /test  |    被保护的资源    |    ROLE_USER     |         Y         |
|  GET   | /login |      登录页面      |       (X)        |         Y         |
|  POST  | /login |      用户认证      |       (X)        |         N         |
|  GET   | /error | 4xx 5xx Error page |       (X)        |         Y         |

## 如何运行?

默认用户名和密码: `radon`

## Q&A

### 1. spring security 从5.0 版本之后需要显式提供`PasswordEncoder`

因为没有涉及到持久层, 自己写了一个简单的`passwordEncoder`

```JAVA
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
```



### 2. Spring Security中的权限控制?

总的来说, Spring Security中权限控制和角色控制是保持一致的. 

例如, `user.hasAuthority("ROLE_USER")` 和 `user.hasRole("USER")`是一样的.

对资源进行权限控制的关键代码:

```JAVA
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/test")
                .hasAuthority("ROLE_USER")

                .and()
                .csrf().disable();
    }
```





### 3. 自定义登录页面和AJAX登录的实现?

先看一下配置:

```JAVA
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
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
```

至于AJAX登录的实现, 正如上面一样, 设置的forwardUrl会走到Spring mvc的, 使用Spring mvc的RESTful功能.

```JAVA
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
```