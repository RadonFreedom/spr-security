# v2.0 Spring Security Oauth2三种模式的实现

> 为了让 [tryboot](https://github.com/RadonFreedom) 项目更好实现认证功能, 希望能集成 Spring Security.



v2.0版本使用Spring Security实现了Oauth2三种模式.

**三种模式的模型和流程请参考 [RFC6749](https://tools.ietf.org/html/rfc6749#section-4.1).**

三种模式的应用场景:

- 授权码模式 (Authorization Code Grant): 三方应用调用本方API获取后台资源.

  流程复杂, 不仅需要用户认证, 还需要客户端提供 `client_id` 和 `client_secret` .

- 用户密码模式 (Resource Owner Password Credentials Grant): 本方前端UI获取后台资源.
__
  仅需要一次性的用户认证和客户端认证即可获取 `token`.

- 客户端认证模式 (Client Credentials Grant): 后端微服务之间REST API相互调用获取资源.

  仅需要客户端提供 `client_id` 和 `client_secret` 即可完成认证, 获取`token`.



认证流程的细节内容请参见 [Spring security笔记](https://github.com/RadonFreedom/notes/blob/master/spring/Spring%20Security.md).

