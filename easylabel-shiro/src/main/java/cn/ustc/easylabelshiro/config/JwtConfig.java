package cn.ustc.easylabelshiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    public static String SECRET;
    @Value("${jwt.expiration}")
    public static String EXPIRATION;
    @Value("${jwt.algorithm}")
    public static String ALGRITHM;
    @Value("${jwt.typ}")
    public static String TYPE;
}
