package com.hurpods.springboot.hurpodsblog.security.constans;

public class SecurityConstants {
    //登陆地址
    public static final String AUTH_LOGIN_URL = "/auth/login";

    //默认登录失效一天
    public static final long EXPIRATION = 24 * 60 * 60L;

    //rememberMe登录失效10天
    public static final long EXPIRATION_REMEMBER = 24 * 7 * 60 * 60L;

    //角色key
    public static final String ROLE_CLAIMS = "rol";

    //jwt密钥
    public static final String JWT_SECRET="SHVycG9kc+WKoOWvhkJhc2U2NEVuY29kZXIxMjM0NTY3ODlIdXJwb2RzUUNxaW5jb25nMTE1cWM=";

    //jwt相关要求
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    private SecurityConstants() {
    }
}
