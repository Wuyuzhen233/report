package com.example.report.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

/**
 * 根据JWT的标准，这些claims可以分为以下三种类型：
 * a. Reserved claims（保留），它的含义就像是编程语言的保留字一样，属于JWT标准里面规定的一些claim。JWT标准里面定好的claim有：
 * iss(Issuser)：代表这个JWT的签发主体；
 * sub(Subject)：代表这个JWT的主体，即它的所有人；
 * aud(Audience)：代表这个JWT的接收对象；
 * exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
 * nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
 * iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
 * jti(JWT ID)：是JWT的唯一标识。
 * b. Public claims，略（不重要）
 * c. Private claims，这个指的就是自定义的claim。比如前面那个结构举例中的admin和name都属于自定的claim。这些claim跟JWT标准规定的claim区别在于：JWT规定的claim，JWT的接收方在拿到JWT之后，都知道怎么对这些标准的claim进行验证；而private claims不会验证，除非明确告诉接收方要对这些claim进行验证以及规则才行。
 *
 * Created by aimu on 2018/12/14.
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "secret";
    private static final String ISS = "echisan";

    // 角色的key
    private static final String ROLE_CLAIMS = "rol";

    // 过期时间是3600秒，既是1个小时(3600L)
    private static final long EXPIRATION = 600L;

    // 选择了记住我之后的过期时间为7天(604800L)
    private static final long EXPIRATION_REMEMBER = 1200L;

    // 创建token
    public static String createToken(String username, String role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)//SECRET_KEY是加密算法对应的密钥，这里使用HS512加密算法
                .setClaims(map)//该方法是在JWT中加入KV字段
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())//设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))//设置过期时间，expTime是过期时间
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    // 获取用户角色
    public static String getUserRole(String token) {
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }

    // 是否已过期
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()//解析JWT字符串中的数据，并进行最基础的验证
                .setSigningKey(SECRET)//SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法
                .parseClaimsJws(token)//jwt是JWT字符串
                .getBody();
    }
}

