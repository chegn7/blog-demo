package xyz.cheng7.blog.util;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.gson.io.GsonDeserializer;
import io.jsonwebtoken.gson.io.GsonSerializer;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt(json web token) 工具类
 */
public class JWTUtil {
    // header + payload 用密钥加密
    private static final String JWT_KEY = "[Wh.2SKyhhh0HxY9'r!C!![W3vWtGKCZrN2cA@8?EljZh2aMaDUE!:JIH),?EkW?~mo~FRR9>&t755UMa^9H;HNvBCI#q*3(hE^/ION$%?sfRQt,2iN<5H]D:N&>JA]%6{5h7bARi[rpBFV?Ed(y4LDMQ7B)',_VD3TSP3Qs#Nqi(bdORrh*zTu4lGMz%gcwN6mk'*nq1}1EBRTTF}TM0$h]#3(9hqO6a~#z1qG^jQI{}J&TQb3S$Y2<b8*tsfB@";

    private static Long TokenValidityMillis = 24 * 3600 * 1000 * 1L; // 1天有效期

    private static SecretKey secretKey;

    static {
        String base64EncodedSecretKey = Base64.encodeBase64String(JWT_KEY.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        /**
         * HS256 使用同一个「secret_key」进行签名与验证。一旦 secret_key 泄漏，就毫无安全性可言了。
         * 因此 HS256 只适合集中式认证，签名和验证都必须由可信方进行。
         * RS256 是使用 RSA 私钥进行签名，使用 RSA 公钥进行验证。公钥即使泄漏也毫无影响，只要确保私钥安全就行。
         * RS256 可以将验证委托给其他应用，只要将公钥给他们就行。
         * ES256 和 RS256 一样，都使用私钥签名，公钥验证。算法速度上差距也不大，但是它的签名长度相对短很多（省流量），并且算法强度和 RS256 差不多。
         * 推荐ES256
         */

        JwtBuilder jwtBuilder = Jwts.builder()
                .serializeToJsonWith(new GsonSerializer<>(new Gson()))
                .signWith(secretKey)
                .setClaims(claims)// body数据， 自行设置，要唯一
                .setIssuedAt(new Date())// 设置签发时间，使得每次签发的结果不一样
                .setExpiration(new Date(System.currentTimeMillis() + TokenValidityMillis));
        return jwtBuilder.compact();
    }

    public static Map<String, Object> checkToken(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .deserializeJsonWith(new GsonDeserializer<>(new Gson()))
                    .setSigningKey(secretKey)
                    .build();
            Jwt parse = jwtParser.parse(jwt);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
