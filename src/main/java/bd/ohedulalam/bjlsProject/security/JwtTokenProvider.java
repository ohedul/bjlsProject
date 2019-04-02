package bd.ohedulalam.bjlsProject.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
* This is a utility class which will generate JWT token after successful login
* and validate JWT token sent in authorization header request.
*/
@Component
public class JwtTokenProvider {
    // Logger will print messages on console.
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    /*
    * We need a secret for jwt and it's expiry time,
    * we set these two variable on application.properties of resource folder
    * we just call these variable here.
    */
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationsInMs}")
    private int jwtExpirationsInMs;

    public String generateToken(Authentication authentication){
        MemberPrincipal memberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationsInMs);

        /*
        * we build a JWT  that have registered claim subject set to member id
        * setting issue and expiration time, then signing the key(jwtSecret) with
        * secure hash algorithm SHA512 and compacting it into a String value
        * then return it.
        */
        return Jwts.builder().setSubject(Long.toString(memberPrincipal.getId()))
                .setIssuedAt(new Date()).setExpiration(expiration).signWith(SignatureAlgorithm.HS512
                , jwtSecret).compact();
    }

    public Long getUserIdFromJwt(String token){
        /*
        * we used our jwtSecret key to validate the token
        * then extract the member id
        */
        Claims claims = Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());

    }

    public boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException ex){
            logger.error("Invalid jwt signature!");
        }catch (MalformedJwtException ex){
            logger.error("Invalid jwt token!");
        }catch (ExpiredJwtException ex){
            logger.error("Expired jwt token!");
        }catch (UnsupportedJwtException ex){
            logger.error("Unsupported jwt token!");
        }catch (IllegalArgumentException ex){
            logger.error("Jwt claims string is empty!");
        }

        return false;
    }


}
