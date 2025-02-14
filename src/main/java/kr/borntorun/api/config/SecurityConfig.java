package kr.borntorun.api.config;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.XXssConfig;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import kr.borntorun.api.config.jwt.AuthenticationPrincipalArgumentResolver;
import kr.borntorun.api.config.jwt.JwtAccessDeniedHandler;
import kr.borntorun.api.config.jwt.JwtAuthenticationEntryPoint;
import kr.borntorun.api.config.jwt.TokenAuthenticationPrincipalArgumentResolver;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.http.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  implements WebMvcConfigurer {

  @Value("${jwt.private-key-path}")
  private String privateKeyPath;

  @Value("${jwt.public-key-path}")
  private String publicKeyPath;

  @Value("${jwt.private-key-pwd}")
  private String privateKeyPassword;

  @Value("${cors.origin}")
  private String origin;

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final Converter<JwtAuthenticationToken, TokenDetail> jwtToTokenConverter;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(origin)
//        .allowCredentials(credentials)
        .allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name());
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new TokenAuthenticationPrincipalArgumentResolver(jwtToTokenConverter));
    resolvers.add(new AuthenticationPrincipalArgumentResolver());
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity, final AuthorizationFilter authorizationFilter) throws Exception {
    final String usersBased = "/api/v1/users";
    final String feedsBased = "/api/v1/feeds";
    final String marathonBookmarkBased = "/api/v1/marathons/bookmark";
    final String commentBased = "/api/v1/comments";
    final String objectStorageBased = "/api/v1/object-storage";
    final String recommendationBased = "/api/v1/recommendation";
    final String activityBased = "/api/v1/activities";
    final String recentSearchKeywordBased = "/api/v1/recent-search-keyword";
    final String privacyBased = "/api/v1/privacy";

    return httpSecurity
        .headers(headers -> headers
            .xssProtection(XXssConfig::disable)
        )
        .httpBasic(HttpBasicConfigurer::disable)
        .csrf(CsrfConfigurer::disable)
        .cors(Customizer.withDefaults())
        .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(oauth2ResourceServer ->
            oauth2ResourceServer
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
        )
        .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(authenticationManager -> authenticationManager
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler))
        .authorizeHttpRequests(
            auth -> auth.requestMatchers(HttpMethod.POST, "/api/v1/auth/sign-out", "/api/v1/auth/refresh").authenticated()  // 로그아웃/토큰갱신
                .requestMatchers(HttpMethod.GET,
//                    activityBased + "/**", // 모임
                    privacyBased + "/user",
                    usersBased + "/my" // 나의 회원 정보 조회
                )
                .authenticated()

                .requestMatchers(HttpMethod.PUT,
                    usersBased + "/sign-up",  // 회원 가입
                    usersBased, // 회원 정보 수정
                    feedsBased + "/{feedId}", // 피드 수정
                    activityBased + "/**", // 모임
                    privacyBased + "/user",
                    commentBased + "/{commentId}"  // 댓글 수정
                )
                .authenticated()

                .requestMatchers(HttpMethod.DELETE,
                    usersBased,  // 회원 탈퇴
                    feedsBased + "/{feedId}", // 피드 삭제
                    commentBased + "/{commentId}",  // 댓글 삭제
                    objectStorageBased + "/{bucket}/{fileId}", // 파일 삭제
                    recommendationBased + "/{recommendationType}/{contentId}", // 좋아요 취소
                    activityBased + "/**", // 모임
                    recentSearchKeywordBased, // 최근 검색서 전체 삭제
                    recentSearchKeywordBased + "/{keyword}", // 최근 검색서 선택 삭제
                    marathonBookmarkBased + "/{marathonId}"  // 대회 북마크 취소
                )
                .authenticated()

                .requestMatchers(HttpMethod.POST,
                    commentBased + "/{feedId}",  // 댓글 작성
                    objectStorageBased + "/{bucket}", // 파일 업로드
                    recommendationBased + "/{recommendationType}/{contentId}", // 좋아요
                    activityBased + "/**", // 모임
                    marathonBookmarkBased + "/{marathonId}"  // 대회 북마크
                )
                .authenticated()
                .anyRequest().permitAll())
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

  @Bean
  public RSAKey rsaKey() throws Exception {
    RSAPrivateKey privateKey = loadPrivateKey(privateKeyPath, privateKeyPassword);
    RSAPublicKey publicKey = loadPublicKey(publicKeyPath);

    return new RSAKey.Builder(publicKey)
      .privateKey(privateKey)
      .keyID(UUID.randomUUID().toString())
      .build();
  }

  private RSAPrivateKey loadPrivateKey(String filePath, String password) throws Exception {
    try (PEMParser pemParser = new PEMParser(new FileReader(filePath))) {
      Object object = pemParser.readObject();

      JcaPEMKeyConverter converter = new JcaPEMKeyConverter();

      if (object instanceof PKCS8EncryptedPrivateKeyInfo encryptedPrivateKeyInfo) {
        // 🔹 암호화된 PKCS#8 개인 키를 복호화
        InputDecryptorProvider decryptorProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder()
          .setProvider(new BouncyCastleProvider())
          .build(password.toCharArray());

        PrivateKeyInfo privateKeyInfo = encryptedPrivateKeyInfo.decryptPrivateKeyInfo(decryptorProvider);
        return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
      } else if (object instanceof PrivateKeyInfo) {
        return (RSAPrivateKey) converter.getPrivateKey((PrivateKeyInfo) object);
      } else {
        throw new IllegalArgumentException("Invalid private key format");
      }
    }
  }

  private RSAPublicKey loadPublicKey(String filePath) throws Exception {
    String keyContent = Files.readString(Paths.get(filePath))
      .replaceAll("-----BEGIN PUBLIC KEY-----", "")
      .replaceAll("-----END PUBLIC KEY-----", "")
      .replaceAll("\\s", "");

    byte[] decodedKey = Base64.getDecoder().decode(keyContent);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, context) -> jwkSelector.select(jwkSet);
  }

  @Bean
  public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
    return NimbusJwtDecoder
        .withPublicKey(rsaKey.toRSAPublicKey())
        .build();
  }
}
