package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import top.king.config.security.JwtUser;
import top.king.entity.User;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


/**
 * jwt工具类
 *
 * @package utils
 * @date 2020-09-26
 */
public class JwtTokenUtil {
    /**
     * 临时盐值，后续每个用户单独一个
     */
    public static String secret = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 到期时间
     */
    public static long expire = 1000 * 60 * 60;

    // 从内存加载secret
    public static String loadKey() {
        return "abcdefghijklmnopqrstuvwxyz";
    }

    // 生成jwt令牌
    public static String generateToken(JwtUser jwtUser) {
        try {
            User user = jwtUser.getUser();
            Map<String, Object> claims = new HashMap<>(4);
            // 对内容简单编码
            claims.put(Convert.str2Hex("uniqueId"), Convert.str2Hex(user.getUniqueId().toString()));
            claims.put(Convert.str2Hex("username"), Convert.str2Hex(user.getUsername()));
            claims.put(Convert.str2Hex("authorities"), Convert.str2Hex(new ObjectMapper().writeValueAsString(jwtUser.getAuthorities())));
            claims.put(Convert.str2Hex("expire"), Convert.str2Hex(String.valueOf(Instant.now().toEpochMilli() + expire)));
            return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, loadKey()).compact();
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    // 解析令牌
    public static Jws<Claims> parseToken(String token) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(loadKey()).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return claimsJws;
    }

    // 判断是否过期
    public static boolean verityExpire(String token) {
        try {
            Jws<Claims> claimsJws = parseToken(token);
            Claims body = claimsJws.getBody();
            Object val;
            // 获得到期日期
            if ((val = body.get(Convert.str2Hex("expire"))) != null) {
                if (Instant.now().toEpochMilli() <= Long.parseLong(Convert.hex2Str((String) val))) {
                    return true;
                }
            }
            /*Map<String, Object> map = new HashMap<>(4);
            // 解码内容
            body.forEach((k, v) -> {
                map.put(Convert.hex2Str(k), v instanceof String ? Convert.hex2Str((String) v) : v);
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 校验令牌,对解析之后的内容使用salt再次加密与请求中的sign比较
    public static boolean validateToken(String token) {
        try {
            // 校验是否被篡改
            Jws<Claims> claimsJws = parseToken(token);
            Claims body = claimsJws.getBody();
            String verifyToken = Jwts.builder().setClaims(body).signWith(SignatureAlgorithm.HS256, loadKey()).compact();
            boolean notModify = verifyToken.equals(token);
            // 校验用户是否存在
            // 校验是否到期
            return notModify && verityExpire(token);
        } catch (Exception e) {
            return false;
        }
    }

    // 刷新令牌
    // 简单加密内容
}
