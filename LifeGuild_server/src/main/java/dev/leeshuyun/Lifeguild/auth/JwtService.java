package dev.leeshuyun.Lifeguild.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.json.Json;
import jakarta.json.JsonObject;

/*
 * JWT related tasks include creating and decoding the jwt tokens, and checking validity
 */
@Service
public class JwtService {

    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    // email is the primary key we use to identify users for this app
    public String extractUsername(String jwtToken) {
        // pick out the "sub" subject claim, this is usually username, but in our
        // case is email
        return extractClaim(jwtToken, Claims::getSubject);
    }

    // extracts a single Claim, returns generic type
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        logger.info("extractClaims>> %s".formatted(claims.toString()));
        return claimsResolver.apply(claims);
    }

    // generate JWT token with only userDetails
    public JsonObject generateToken(UserDetails userDetails) {
        String jwtStr = generateToken(new HashMap<>(), userDetails);
        JsonObject jwtJson = Json.createObjectBuilder()
                .add("jwt", jwtStr)
                .build();
        return jwtJson;
    }

    // generate JWT token with extra claims and user details
    // spring always has "username" as primary key
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // dates for checking if token is expired
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // token is valid for 24hrs
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact(); // generate the token
    }

    // TODO
    // OAuth generate the refresh token for expired access tokens
    // public String generateRefreshToken(){
    // return Jwts
    // .builder()
    // }

    // does jwtToken belong to this user
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    // decode and extract all claims from payload using the signin key
    // signing key is a secret used to sign the JWT token signature
    // it's used together with the sign-in algo (HMAC, SHA256 etc) that is specified
    // in the header
    // key size (minimum 256-bit for JWT) and algo used depends on
    // security requirements of app and level of trust in the signing party
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder() // parses the token
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken) // process the token
                .getBody();
    }

    private Key getSignInKey() {
        // decode our secret key
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        // using the HMAC and SHA256 algos, generate the signin secret key
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
