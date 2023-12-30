package cn.ustc.easylabelshiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static cn.ustc.easylabelshiro.config.JwtConfig.*;

@Component
@Slf4j
public class JwtUtil {

    /**
     * 生成token
     */
    public static String generateToken(String username) {
        SecretKey signingKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        LocalDateTime tokenExpireTime = LocalDateTime.now().plusMinutes(EXPIRE);
        return Jwts.builder()
                .signWith(signingKey, Jwts.SIG.HS256)
                .header().add("typ", TYPE).and()
                .issuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .subject(username)
                .expiration(Timestamp.valueOf(tokenExpireTime))
                .claims(Map.of("username", username))
                .compact();
    }

    public static Claims getClaimsByToken(String token) {
        SecretKey signingKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 检查token是否过期
     * return true则为过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    /**
     * 获取token中的信息，通常是username，无需secret解密也能获得
     */
    public static String getClaimField(String token, String field) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(field).asString();
        } catch (JWTDecodeException e) {
            log.error("JwtUtil getClaimFiled error: ", e);
            return null;
        }

    }

}
