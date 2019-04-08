package fre.shown.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

/**
 *
 * 用于从yml配置中装载 Oauth2 认证服务器对客户端认证信息的配置.<p/>
 *
 * 配置实例如下:
 * <pre class="yml">
 * security:
 *   oauth2:
 *     server:
 *       clients:
 *         # 客户端名, 这个名字是无用的, 最后只会取值
 *         client:
 *           # client-id中的名字是有用的
 *           client-id: client
 *           secret: radon
 *           authorized-grant-types:
 *             - authorization_code
 *             - refresh_token
 *           redirectUris: /redirect
 *           scopes: client
 *
 *         web:
 *           client-id: web
 *           secret: radon
 *           authorized-grant-types:
 *             - password
 *             - refresh_token
 *           scopes: ui
 *
 *         account-service:
 *           client-id: account-service
 *           secret: radon
 *           authorized-grant-types:
 *             - client_credentials
 *             - refresh_token
 *           scopes: server
 * </pre>
 * @author Radon Freedom
 * created at 2019.04.04 上午8:49
 */


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

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String[] getAuthorizedGrantTypes() {
            return authorizedGrantTypes;
        }

        public void setAuthorizedGrantTypes(String[] authorizedGrantTypes) {
            this.authorizedGrantTypes = authorizedGrantTypes;
        }

        public String[] getAuthorities() {
            return authorities;
        }

        public void setAuthorities(String[] authorities) {
            this.authorities = authorities;
        }

        public Integer getAccessTokenValiditySeconds() {
            return accessTokenValiditySeconds;
        }

        public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
            this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        }

        public Integer getRefreshTokenValiditySeconds() {
            return refreshTokenValiditySeconds;
        }

        public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
            this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        }

        public String[] getScopes() {
            return scopes;
        }

        public void setScopes(String[] scopes) {
            this.scopes = scopes;
        }

        public String[] getAutoApproveScopes() {
            return autoApproveScopes;
        }

        public void setAutoApproveScopes(String[] autoApproveScopes) {
            this.autoApproveScopes = autoApproveScopes;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String[] getRedirectUris() {
            return redirectUris;
        }

        public void setRedirectUris(String[] redirectUris) {
            this.redirectUris = redirectUris;
        }

        public String[] getResourceIds() {
            return resourceIds;
        }

        public void setResourceIds(String[] resourceIds) {
            this.resourceIds = resourceIds;
        }

        public boolean isAutoApprove() {
            return autoApprove;
        }

        public void setAutoApprove(boolean autoApprove) {
            this.autoApprove = autoApprove;
        }
    }
}
