package lk.ijse.green_shadow.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.ijse.green_shadow.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTServiceImpl implements JWTService {
    @Value("${spring.jwtkey}")
    private String jwtKey;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(),user);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        var username = extractUserName(token);
        var roles = extractRoles(token);
        boolean isValidRole = roles.contains("ROLE_MANAGER");
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && isValidRole) ;
    }

    @Override
    public String refreshToken(UserDetails userDetails) {
        return refreshToken(new HashMap<>(),userDetails);
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimResolve) {
        final Claims claims = getAllClaims(token);
        return claimResolve.apply(claims);
    }

    private List<String> extractRoles(String token) {
        final Claims claims = getAllClaims(token);
        return claims.get("roles", List.class);  // Get the roles from the claims
    }

    private String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(role -> role.startsWith("ROLE_")? role: "ROLE_" + role)
                        .collect(Collectors.toList());
        extractClaims.put("role",roles);
        Date now = new Date();
        Date expire = new Date(now.getTime() + 1000 * 600);

        return Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

    }
    private String refreshToken(Map<String,Object> extractClaims,UserDetails userDetails){
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.startsWith("ROLE_")? role: "ROLE_" + role)
                .collect(Collectors.toList());
        extractClaims.put("role",roles);
        Date now = new Date();
        Date expire = new Date(now.getTime() + 1000 * 600);
        Date refreshExpire = new Date(now.getTime() + 1000 * 600 * 600);

        return Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(refreshExpire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token)
                .getBody();
    }
    private Key getSignKey(){
        byte[] decode = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(decode);
    }
}
