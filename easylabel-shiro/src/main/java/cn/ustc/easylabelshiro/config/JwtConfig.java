package cn.ustc.easylabelshiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    public static String SECRET;
    @Value("${jwt.expiration}")
    public static long EXPIRE;
    @Value("${jwt.header}")
    public static String HEADER;
    @Value("${jwt.typ}")
    public static String TYPE;
}
