# v2.0 Spring Security Oauth2三种模式的实现

> 为了让 [tryboot](https://github.com/RadonFreedom) 项目更好实现认证功能, 希望能集成 Spring Security.



v2.0版本使用Spring Security实现了Oauth2三种模式.

**三种模式的模型和流程请参考 [RFC6749](https://tools.ietf.org/html/rfc6749#section-4.1).**

三种模式的应用场景:

- 授权码模式 (Authorization Code Grant): 三方应用调用本方API获取后台资源.

  流程复杂, 不仅需要用户认证, 还需要客户端提供 `client_id` 和 `client_secret` .

- 用户密码模式 (Resource Owner Password Credentials Grant): 本方前端UI获取后台资源.

  仅需要一次性的用户认证和客户端认证即可获取 `token`.

- 客户端认证模式 (Client Credentials Grant): 后端微服务之间REST API相互调用获取资源.

  仅需要客户端提供 `client_id` 和 `client_secret` 即可完成认证, 获取`token`.



认证流程的细节内容请参见 [Spring security笔记](https://github.com/RadonFreedom/notes/blob/master/spring/Spring%20Security.md).



## 使用yml自定义Oauth2客户端配置

**为了能够方便配置客户端认证信息, 实现配置信息和java代码解耦, 打算从yml配置中装载 Oauth2 认证服务器对客户端认证信息的配置.**

**我写的`Oauth2ServerClientsProperties`, 用来装载在yml中配置的客户端认证信息.**

```JAVA
@ConfigurationProperties(prefix = "security.oauth2.server")
public class Oauth2ServerClientsProperties {

    private Map<String, Oauth2ClientProperties> clients = new LinkedHashMap<>();

    public Map<String, Oauth2ClientProperties> getClients() {
        return clients;
    }

    public void setClients(Map<String, Oauth2ClientProperties> clients) {
        this.clients = clients;
    }

    public static class Oauth2ClientProperties {

        private String clientId;

        private String[] authorizedGrantTypes = {};

        private String[] authorities = {};

        private Integer accessTokenValiditySeconds;

        private Integer refreshTokenValiditySeconds;

        private String[] scopes = {};

        private String[] autoApproveScopes = {};

        private String secret;

        private String[] redirectUris = {};

        private String[] resourceIds = {};

        private boolean autoApprove;

        //getter&setter省略
    }
}

```

**对应的yml配置也可以当做以后的范本**:

```YML
security:
  oauth2:
    server:
      clients:
        client:
          client-id: client
          secret: radon
          authorized-grant-types:
            - authorization_code
            - refresh_token
          redirectUris: /redirect
          scopes: client

        web:
          client-id: web
          secret: radon
          authorized-grant-types:
            - password
            - refresh_token
          scopes: ui

        account-service:
          client-id: account-service
          secret: radon
          authorized-grant-types:
            - client_credentials
            - refresh_token
          scopes: server

```

**最后是取出值并传入原配置方式之中**:

```JAVA
@EnableAuthorizationServer
//引入装载的配置
@EnableConfigurationProperties(Oauth2ServerClientsProperties.class)
@Configuration
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
    
        @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        //基于YML配置, 请在配置文件中添加相关配置
        InMemoryClientDetailsServiceBuilder inMemoryClientDetailsServiceBuilder = 
            clients.inMemory();

        //取出clients中的值并一一配置, Oauth2ClientProperties和官方的ClientBuilder是对应关系
        for (Oauth2ServerClientsProperties.Oauth2ClientProperties client : 
             oauth2ServerClientsProperties.getClients().values()) {
            ClientDetailsServiceBuilder.ClientBuilder builder = 
                inMemoryClientDetailsServiceBuilder
                //withClient会new一个ClientBuilder返回
                    .withClient(client.getClientId())
                    .authorizedGrantTypes(client.getAuthorizedGrantTypes())
                    .authorities(client.getAuthorities())
                    .scopes(client.getScopes())
                    .autoApprove(client.getAutoApproveScopes())
                    .autoApprove(client.isAutoApprove())
                    .secret(client.getSecret())
                    .redirectUris(client.getRedirectUris())
                    .resourceIds(client.getResourceIds());

            //由于配置方法的参数是原始类型, 必须进行非空校验再传入
            if (client.getAccessTokenValiditySeconds() != null) {
                builder.accessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
            }
            if (client.getAccessTokenValiditySeconds() != null) {
                builder.refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
            }
        }
    }
}
```



## REDIS用于token存储

如果token存储在服务的JVM进程内存中, 一旦这个服务崩溃, token信息将全部丢失.

为了避免这种情况的发生, 尝试使用redis进行token存储

#### 使用DOCKER 开启 REDIS

```
> docker run --name myredis -p6379:6379 redis &
```

#### 配置token存储, 使用`RedisTokenStore`

```JAVA
@EnableAuthorizationServer
@EnableConfigurationProperties(Oauth2ServerClientsProperties.class)
@Configuration
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {


    private final AuthenticationManager authenticationManager;
    private final Oauth2ServerClientsProperties oauth2ServerClientsProperties;
    private final RedisTokenStore redisTokenStore;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
            	//使用REDIS存储token
                .tokenStore(redisTokenStore)
                //注入authenticationManager来支持 password grant type
                .authenticationManager(authenticationManager);
    }

    @Bean
    @Autowired
    public RedisTokenStore redisTokenStore (RedisConnectionFactory redisConnectionFactory) {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
```

存储结果:

```
radon@boat:~/Desktop/dev/spr-security$ docker exec -it myredis redis-cli
127.0.0.1:6379> ping
PONG

127.0.0.1:6379> keys *
(empty list or set)

127.0.0.1:6379> keys *
1) "client_id_to_access:web"
2) "access:6b9334ff-32fb-4fa7-a159-10418684db90"
3) "auth:6b9334ff-32fb-4fa7-a159-10418684db90"
4) "refresh_to_access:d1936aa5-5887-43ae-81f5-8f79c9142cfa"
5) "uname_to_access:web:radon"
6) "access_to_refresh:6b9334ff-32fb-4fa7-a159-10418684db90"
7) "refresh:d1936aa5-5887-43ae-81f5-8f79c9142cfa"
8) "refresh_auth:d1936aa5-5887-43ae-81f5-8f79c9142cfa"
9) "auth_to_access:0ce3e5f8acd507206de3967878c21cd8"

```



## 注意事项

#### 1. scope

不同模式下scope的决定方式不同, 在授权码模式下由对`GET /oauth/authorize`的请求参数决定, 而用户密码认证模式和客户端认证模式下由`/oauth/authorize`决定.

#### 2. spring security 的 token获取机制

相同的用户和客户端两次获取token, 如果相差时间没有超过第一次获取token时的失效时间, 返回的将是相同的token.

