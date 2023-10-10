package com.starta.project.global.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starta.project.domain.member.entity.RefreshToken;
import com.starta.project.domain.member.entity.UserRoleEnum;
import com.starta.project.domain.member.repository.RedisRepository;
import com.starta.project.domain.member.service.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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


@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {

    //쿠키를 직접 만들어서 토큰을 담아 쿠키를 Response 객체에 담아 반환

    // JWT 데이터
    // accessToken 값, Header name, 권환 이름 (user or admin)
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String REFRESH_HEADER = "RefreshToken";

    //redis 값 조회 헤더
    public static final String REFRESH_PREFIX = "refresh:";

    // 사용자 권한 값의 KEY, 권한을 구분하기 위함
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
//    private final long TOKEN_TIME = 3 * 60 * 1000L; // Test용 3분

    private final RedisRepository redisRepository;

    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.secret.key}")
    private String secretKey; //jwt.secret.key
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    // 생성자 호출 뒤에 실행, 요청의 반복 호출 방지
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    //JWT 생성
    //토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한은 key 값으로 꺼내어 쓸 수 있다.
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    // 기존의 addJwtToCookie 메서드를 변경하여 헤더에 토큰을 추가
    public void addJwtToHeader(String token, HttpServletResponse res) {
        res.setHeader(AUTHORIZATION_HEADER, token);
    }

    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // 토큰 검증, JWT 위변조 확인
    // parseBuilder() : 구성 성분을 분해하고 분석
    public String validateToken(String accessToken, String refreshTokenValue, HttpServletResponse res) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return accessToken;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new JwtException("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
//
//            //refresh 토큰 값전달해서 유효 확인
//            String value = redisRepository.getValue(REFRESH_PREFIX + refreshTokenValue);
//            if (value == null) { // refresh 만료
//                logger.error("Expired JWT token, 만료된 JWT token 입니다.");
//                throw new JwtException("Expired JWT, 만료된 JWT 입니다.");
//            }
//            try {
//                ObjectMapper objectMapper = new ObjectMapper();
//
//                //refreshToken 값
//                RefreshToken refreshToken = objectMapper.readValue(value, RefreshToken.class);
//
//                String username = refreshToken.getUsername();
//                UserRoleEnum role = refreshToken.getRole();
//                //access 토큰 다시 발급 (Bearer ~~)
//                accessToken = createToken(username, role);
//
//                //Refresh Token Rotation (기존 Refresh 토큰 제거 후 새로 발급)
//                Long refreshExpireTime = refreshTokenService.getRefreshTokenTimeToLive(REFRESH_PREFIX + refreshTokenValue);
//                redisRepository.setExpire(REFRESH_PREFIX + refreshTokenValue, 0L);
//
//                refreshTokenService.refreshTokenRotation(username, role, refreshExpireTime); // 새로생성
//
//                addJwtToHeader(accessToken, res);
//                //Bearer 제거
//                accessToken = substringToken(accessToken);
//                return accessToken;

            // 임시처리
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new JwtException("Expired JWT token, 만료된 JWT token 입니다.");
//            } catch (JsonProcessingException ex) {
//                throw new RuntimeException(ex);
//            }
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

    public String getTokenFromRequest(HttpServletRequest req) {
        return req.getHeader(AUTHORIZATION_HEADER);
    }

    public String getRefreshTokenFromRequest(HttpServletRequest req) {
        return req.getHeader(REFRESH_HEADER);
    }

    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
