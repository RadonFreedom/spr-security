package fre.shown.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author Radon Freedom
 * created at 2019.04.01 下午6:29
 */


@EnableAuthorizationServer
@Configuration
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {


    @Value("${oauth2.server.client-secret.account-service}")
    private String ACCOUNT_SERVICE_PASSWORD;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public Oauth2ServerConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                //url:/oauth/token_key,exposes public key for token verification if using JWT tokens
                .tokenKeyAccess("permitAll()")
                //url:/oauth/check_token allow check token
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // @formatter:off
        clients.inMemory()
                .withClient("client")
                .secret("radon")
                //设置授权类型
                .authorizedGrantTypes("authorization_code", "refresh_token")
                /*
                在Spring Security中
                oauth2服务器必须显式地配置redirect_uri
                oauth2客户端必须在请求中提供redirect_uri
                这是和参考模型不同之处
                 */
                .redirectUris("/redirect")
                .scopes("client")

                .and()
                .withClient("web")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("ui")

                .and()
                .withClient("account-service")
                .secret(ACCOUNT_SERVICE_PASSWORD)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server");
        // @formatter:on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                //注入authenticationManager来支持 password grant type
                .authenticationManager(authenticationManager);
    }
}
