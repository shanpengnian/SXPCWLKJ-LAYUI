package com.sxpcwlkj.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sxpcwlkj.entity.Member;
import com.sxpcwlkj.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.lhym.applet.config
 * @date 2020/11/2
 * 生成Token
 */
public class TokenUtil {

    /**
     * 过期时间为一天
     * TODO 正式上线更换为30分钟
     */
    private static final long EXPIRE_TIME = 30* 60 * 1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "189m2JIXqAWz8SM62zesfg==";

    /**
     * 生成签名
     * 默认30分钟失效
     * 不能解密参数
     * @param username
     * @param userId
     * @return
     */
    public static String getToken(String username, String userId) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头信息
            HashMap<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //附带username和userID生成签名
            return JWT.create().withHeader(header)
                    .withClaim("loginName", username)
                    .withClaim("userId", userId)
                    .withExpiresAt(date).sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成ekt
     *
     * @param username
     * @param ID
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getTokenGetId(String username, String ID) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = "";
        try {
            //私钥及加密算法
            Algorithm algorithm = null;
            algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头信息
            HashMap<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //附带username和userID生成签名
            token = JWT.create().withHeader(header)
                    .withAudience(ID)
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC256(username));
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 验证token
     * 正确返回true  错误返回false
     * @param token
     * @return
     */
    public static boolean getIsToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    /**
     * 解密 获取id
     *
     * @param token
     * @return
     */
    public static String getTokenId(String token) {
        try {
            return JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 生成 Token
     *
     * @param id      ID
     * @param data    自定义参数
     * @param endTiem 具体到期时间
     * @return 返回 token
     */
    public static String getCreateJwt(String id, Map<String, String> data, Date endTiem) {
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(new Date())//时间
                .claim("code", data)//添加自定义的数据
                .setSubject("品创网络")
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .setExpiration(new Date(endTiem.getTime()));//方法用于设置过期时间
        return builder.compact();
    }

    /**
     * 解密 Token
     *
     * @param token
     */
    public static Map<String, Object> getpareJwt(String token) {

        try {
            Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
            //System.out.println("id:" + claims.getId());
            //System.out.println("subject:" + claims.getSubject());
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
            //System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
            //System.out.println("过期时间:" + sdf.format(claims.getExpiration()));
            //System.out.println("当前时间:" + sdf.format(new Date()));
            //System.out.println("自定义数据:" + claims.get("code"));
            Map<String, Object> data = (Map<String, Object>) claims.get("code");
            data.put("id", claims.getId());
            data.put("subject", claims.getSubject());
            data.put("signTime", claims.getIssuedAt());
            data.put("endTime", claims.getExpiration());
            return data;
        } catch (Exception e) {
            return null;
        }


    }


    @SneakyThrows
    public static void main(String[] res) {

        String token = getToken("admin", "123456");



        //System.out.println("正确返回true反之返回false:\n"+getIsToken(token));

        token=getToken("admin", "1");
        //token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxIiwiZXhwIjoxNjEwNDMwOTQ5fQ.lg2ZzR8zl7sr6zn-YlWDtUFwl9YxY90J6LZIr3fwOcQ";
        //休眠5s
        TimeUnit.SECONDS.sleep(10);
        System.out.println("验证并返回ID:\n"+getIsToken(token));



        //Map map = new HashMap();
        //map.put("name", "xijue");
        //map.put("pass", "12346");
        //token = getCreateJwt("1", map, DateUtil.getStrToDate("2221-01-10 14:59:00"));
        //System.out.println(token);
        //TimeUnit.SECONDS.sleep(5);
        //token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxIiwiZXhwIjoxNjEwNDMwNjc4fQ.jXmIfiF6rH9FFq-0KuBNEAPC8eoqKS5Ru9xQ6mPIOxs";
        //System.out.println("返回Map:\n"+getpareJwt(token));
    }

}
