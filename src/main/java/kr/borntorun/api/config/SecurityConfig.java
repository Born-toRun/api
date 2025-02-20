package kr.borntorun.api.config;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.XXssConfig;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.borntorun.api.config.jwt.AuthenticationPrincipalArgumentResolver;
import kr.borntorun.api.config.jwt.JwtAccessDeniedHandler;
import kr.borntorun.api.config.jwt.JwtAuthenticationEntryPoint;
import kr.borntorun.api.config.jwt.TokenAuthenticationPrincipalArgumentResolver;
import kr.borntorun.api.config.properties.AppProperties;
import kr.borntorun.api.config.properties.CorsProperties;
import kr.borntorun.api.domain.constant.RoleType;
import kr.borntorun.api.domain.port.UserPort;
import kr.borntorun.api.domain.port.UserRefreshTokenPort;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.http.filter.AuthorizationFilter;
import kr.borntorun.api.support.oauth.filter.TokenAuthenticationFilter;
import kr.borntorun.api.support.oauth.handler.OAuth2AuthenticationFailureHandler;
import kr.borntorun.api.support.oauth.handler.OAuth2AuthenticationSuccessHandler;
import kr.borntorun.api.support.oauth.handler.TokenAccessDeniedHandler;
import kr.borntorun.api.support.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import kr.borntorun.api.support.oauth.service.BornToRunOAuth2UserService;
import kr.borntorun.api.support.oauth.service.BornToRunUserDetailsService;
import kr.borntorun.api.support.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {CorsProperties.class, AppProperties.class})
public class SecurityConfig  implements WebMvcConfigurer {

  private final CorsProperties corsProperties;
  private final AppProperties appProperties;
  private final AuthTokenProvider tokenProvider;
  private final BornToRunUserDetailsService userDetailsService;
  private final BornToRunOAuth2UserService oAuth2UserService;
  private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
  private final UserRefreshTokenPort userRefreshTokenPort;
  private final UserPort userPort;

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final Converter<JwtAuthenticationToken, TokenDetail> jwtToTokenConverter;

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
        .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(authenticationManager -> authenticationManager
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler))
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/api/**").hasAnyAuthority(RoleType.GUEST.getCode())
              .requestMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
              .anyRequest().authenticated()

              .requestMatchers(HttpMethod.POST, "/api/v1/auth/sign-out", "/api/v1/auth/refresh").authenticated()  // 로그아웃/토큰갱신
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
      .oauth2Login(oauth2 -> oauth2
        .authorizationEndpoint(endpoint -> endpoint
          .baseUri("/oauth2/authorization")
          .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
        .redirectionEndpoint(endpoint -> endpoint
          .baseUri("/*/oauth2/code/*"))
        .userInfoEndpoint(endpoint -> endpoint
          .userService(oAuth2UserService))
        .successHandler(oAuth2AuthenticationSuccessHandler())
        .failureHandler(oAuth2AuthenticationFailureHandler()))
        .build();
  }



  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  /*
   * security 설정 시, 사용할 인코더 설정
   * */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /*
   * 토큰 필터 설정
   * */
  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter(tokenProvider);
  }

  /*
   * 쿠키 기반 인가 Repository
   * 인가 응답을 연계 하고 검증할 때 사용.
   * */
  @Bean
  public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
    return new OAuth2AuthorizationRequestBasedOnCookieRepository();
  }

  /*
   * Oauth 인증 성공 핸들러
   * */
  @Bean
  public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
    return new OAuth2AuthenticationSuccessHandler(
      tokenProvider,
      appProperties,
      userPort,
      userRefreshTokenPort,
      oAuth2AuthorizationRequestBasedOnCookieRepository()
    );
  }

  /*
   * Oauth 인증 실패 핸들러
   * */
  @Bean
  public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
    return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
  }

  /*
   * Cors 설정
   * */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins(corsProperties.getAllowedOrigins().split(","))
      .allowedHeaders(corsProperties.getAllowedHeaders().split(","))
      .allowedMethods(corsProperties.getAllowedHeaders().split(","))
      .allowCredentials(true);
  }
}
