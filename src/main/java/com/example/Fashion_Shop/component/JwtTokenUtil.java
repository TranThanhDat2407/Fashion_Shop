package com.example.Fashion_Shop.component;

import com.example.Fashion_Shop.exception.InvalidParamException;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration; //save to an environment variable

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    @Value("${jwt.secretKey}")
    private String secretKey;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private final TokenRepository tokenRepository;

    //Tạo một JWT token mới với thông tin từ người dùng.
    public String generateToken(User user) throws Exception{
        //properties => claims
        Map<String, Object> claims = new HashMap<>();
        //this.generateSecretKey();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());
        // Thêm claims (dữ liệu bổ sung, như email) vào token.
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail()) //Đặt subject là email người dùng
                    // subject là trường đại diện cho thực thể.
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))// Đặt thời hạn.
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e) {
            //you can "inject" Logger, instead System.out.println
            throw new InvalidParamException("Cannot create jwt token, error: "+e.getMessage());
            //return null;
        }
    }

    //
    private Key getSignInKey() {
        //Giải mã secretKey từ Base64 bằng Decoders.BASE64.decode().
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        //Keys.hmacShaKeyFor(Decoders.BASE64.decode("TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI="));

        //Tạo một đối tượng Key sử dụng Keys.hmacShaKeyFor().
        return Keys.hmacShaKeyFor(bytes);
    }

    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
    // Trích xuất tất cả các claims (dữ liệu tùy chỉnh) từ JWT.
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // Trích xuất một claim cụ thể từ JWT.
    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    // Kiểm tra token có hết hạn hay không.
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    //Trích Subject(Email) từ token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //Xác minh JWT có hợp lệ không.
    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }
}
