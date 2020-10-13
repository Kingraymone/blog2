package utils;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import top.king.entity.User;

import java.security.Key;
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
    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>(4);
        // 对内容简单编码
        claims.put(Convert.str2Hex("nickname"), Convert.str2Hex(StringUtils.isEmpty(user.getNickname()) ? user.getUsername() : user.getNickname()));
        claims.put(Convert.str2Hex("expire"), Convert.str2Hex(String.valueOf(Instant.now().toEpochMilli() + expire)));
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, loadKey()).compact();
    }

    // 解析令牌
    public static Jws<Claims> parseToken(String token) throws Exception {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(loadKey()).parseClaimsJws(token);
        } catch (Exception e) {
            throw new Exception("解析token失败！");
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
            boolean isModify = verifyToken.equals(token);
            // 校验用户是否存在
            // 校验是否到期
            return isModify && verityExpire(token);
        } catch (Exception e) {
            return false;
        }
    }

    // 刷新令牌
    // 简单加密内容
}
