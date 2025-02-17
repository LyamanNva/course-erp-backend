package com.example.course_erp_backend.services.security;

import com.example.course_erp_backend.models.dto.RefreshTokenDto;
import com.example.course_erp_backend.models.mybatis.user.User;
import com.example.course_erp_backend.models.properties.security.SecurityProperties;
import com.example.course_erp_backend.services.base.TokenGenerator;
import com.example.course_erp_backend.services.base.TokenReader;
import com.example.course_erp_backend.services.getters.EmailGetter;
import com.example.course_erp_backend.utils.PublicPrivateKeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.course_erp_backend.constants.TokenConstants.EMAIL_KEY;

@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenManager implements TokenGenerator<RefreshTokenDto>,
        TokenReader<Claims> , EmailGetter {

    private final SecurityProperties securityProperties;

    @Override
    public String generate(RefreshTokenDto obj) {
        final User user = obj.getUser();

        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("type","REFRESH_TOKEN");

        Date now = new Date();
        Date exp = new Date(now.getTime() + securityProperties.getJwt().getRefreshTokenValidityTime(obj.isRememberMe()));
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(claims)
                .signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public Claims read(String token) {
        Claims tokenData= Jwts.parserBuilder()
                .setSigningKey(PublicPrivateKeyUtils.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String typeOfToken=tokenData.get("type",String.class);
        if (!"REFRESH_TOKEN".equals(typeOfToken)){
            throw new RuntimeException("Invalid type of token");
        }
        return tokenData;
    }

    @Override
    public String getEmail(String token) {
        return read(token).get(EMAIL_KEY,String.class);

    }
}
