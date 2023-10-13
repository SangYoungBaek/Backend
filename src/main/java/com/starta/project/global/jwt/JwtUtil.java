package com.starta.project.global.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starta.project.domain.member.entity.RefreshToken;
import com.starta.project.domain.member.entity.UserRoleEnum;
import com.starta.project.domain.member.repository.RedisRepository;
import com.starta.project.domain.member.service.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰 생성 및 검증과 관련된 유틸리티 클래스입니다.
 *
 */
@Slf4j(topic = "JWT 관련 로그")
@Component
@RequiredArgsConstructor
public class JwtUtil {

    // JWT 데이터
    // accessToken 값, Header name, 권환 이름 (user or admin)
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //redis 값 조회 헤더
    public static final String REFRESH_PREFIX = "refresh:";

    // 사용자 권한 값의 KEY, 권한을 구분하기 위함
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME =  60 * 1000L; // TEST 1분, 밀리세컨드

    private final RedisRepository redisRepository;

    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey; //jwt.secret.key
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    // 생성자 호출 뒤에 실행, 요청의 반복 호출 방지
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }


    //토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // key 값으로 꺼내어 쓸 수 있다.
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String getJwtFromHeader(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 헤더에 토큰을 추가
    public void addJwtToHeader(String token, HttpServletResponse res) {
        res.setHeader(AUTHORIZATION_HEADER, token);
    }

    // 토큰 검증, JWT 위변조 확인
    // parseBuilder() : 구성 성분을 분해하고 분석
    public String validateToken(String accessToken, HttpServletResponse res) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return accessToken;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new JwtException("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            //refresh 토큰 값전달해서 유효 확인
            String value = redisRepository.getValue(REFRESH_PREFIX + accessToken);
            if (value == null) { // refresh 만료
                log.error(REFRESH_PREFIX + accessToken);
                log.error("Expired JWT token, 만료된 JWT token 입니다.");
                throw new JwtException("Expired JWT, 만료된 JWT 입니다.");
            }
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                //refreshToken 값
                RefreshToken refreshToken = objectMapper.readValue(value, RefreshToken.class);

                String username = refreshToken.getUsername();
                UserRoleEnum role = refreshToken.getRole();
                //access 토큰 다시 발급 (Bearer ~~)
                String newAccessToken = createToken(username, role);

                //Refresh Token Rotation (기존 Refresh 토큰 제거 후 새로 발급)
                Long refreshExpireTime = refreshTokenService.getRefreshTokenTimeToLive(REFRESH_PREFIX + accessToken);
                redisRepository.setExpire(REFRESH_PREFIX + accessToken, 0L);

                refreshTokenService.refreshTokenRotation(newAccessToken, username, role, refreshExpireTime);

                addJwtToHeader(newAccessToken, res);
                return newAccessToken;

            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new JwtException("Unsupported JWT, 지원되지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new JwtException("JWT claims is empty, 잘못된 JWT 입니다.");
        }
    }

    // 토큰에서 사용자 정보 가져오기
    // Payload 부분에는 토큰에 담긴 정보
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
