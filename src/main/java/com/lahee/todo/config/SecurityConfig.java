package com.lahee.todo.config;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean //메서드의 결과를 bean 객체로 등록해주는 어노테이션
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsFilter corsFilter, AuthenticationEntryPoint ap, AccessDeniedHandler ad) throws Exception {
        http.headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class); //유저 이름과 패스워드 검증 확인

        http.csrf(AbstractHttpConfigurer::disable) //cross site request 방지를 위한 추가(실제 배포 시에는 넣지 않는게 좋다)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(
                        authHttp -> authHttp.requestMatchers("/api/users/check-username", "/users/signup", "/home", "/todo").permitAll() //누구든지 요청하는 허가
//                                .requestMatchers("/users/my-profile").authenticated()
//                                .requestMatchers("/").anonymous()//인증이 되지 않은 사용자만 허가
                                .anyRequest().authenticated() //나머지 요청에 대해서 인증된 사용자만
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/users/login")
                                .defaultSuccessUrl("/home")
                                .failureUrl("/users/login?fail")
                                .permitAll()
                )
                .logout(
                        logout -> logout //로그아웃 으로 포스트 요청을 보내게끔한다.
                                .logoutUrl("/users/logout")
                                .logoutSuccessUrl("/users/login")

                ).exceptionHandling(e -> e.accessDeniedHandler(ad).authenticationEntryPoint(ap))


        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        log.warn("accessDeniedHandler");
        return (request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("ACCESS DENIED");
            response.getWriter().flush();
            response.getWriter().close();
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("UNAUTHORIZED");
            response.getWriter().flush();
            response.getWriter().close();
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
