package com.catalpa.pocket.util;

import com.alibaba.fastjson.JSON;
import com.catalpa.pocket.error.UnAuthorizedAccessException;
import com.catalpa.pocket.model.AccessTokenPayload;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created by bruce on 2018/7/10.
 */
@Slf4j
public class JwtTokenUtil {

    private static final String SECRET_KEY = "JKKLJOoasdlfj";

    public static String buildToken(int expiresIn, AccessTokenPayload accessTokenPayload) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setSubject(JSON.toJSONString(accessTokenPayload)).setIssuedAt(now).signWith(signatureAlgorithm, SECRET_KEY);
        if (expiresIn >= 0L) {
            long expireMillis = nowMillis + expiresIn * 1000L;
            builder.setExpiration(new Date(expireMillis));
        }
        return builder.compact();
    }

    public static AccessTokenPayload validateToken(String token) {
        String errorMessage;
        try {
            String subject = (Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody()).getSubject();
            return JSON.parseObject(subject, AccessTokenPayload.class);
        } catch (ExpiredJwtException e) {
            errorMessage = "expired token: " + token;
            log.error(errorMessage);
            throw new UnAuthorizedAccessException("4013", errorMessage);
        } catch (MalformedJwtException e) {
            errorMessage = "malformed token: " + token;
            log.error(errorMessage);
            throw new UnAuthorizedAccessException("4014", errorMessage);
        } catch (SignatureException e) {
            errorMessage = "signature exception token: " + token;
            log.error(errorMessage);
            throw new UnAuthorizedAccessException("4015", errorMessage);
        }
    }
}
