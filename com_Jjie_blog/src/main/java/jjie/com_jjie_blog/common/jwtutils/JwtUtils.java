package jjie.com_jjie_blog.common.jwtutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.awt.*;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static final long GAP=1000*60*60;
    private static final String secretKey="jjielujjielujjielujjielujjielujjielujjielujjielujjielujjielu" +
            "jjielujjielujjielujjielujjielujjielujjielujjielujjielujjielujjielu";
    public static final Key key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    public static String genToken(Map claims){
        Date now=new Date();
        Date expiration=new Date(now.getTime()+GAP);
        String token= Jwts.builder()
                .setClaims(Jwts.claims(claims))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
        return token;
    }
    public static boolean checkToken(String token){
        if (token == null || token.isBlank()) {
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token 过期、签名错误、格式错误等情况
            return false;
        }
    }
}
