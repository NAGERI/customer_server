package com.compuLynx.banker.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

//@Configuration
//@EnableWebSecurity
//
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    @Autowired
//    private final CustomUserDetailService customUserDetailService;
//    private final UnauthorizedHandler unauthorizedHandler;
//
//
//    @Bean
//    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
//        return new MvcRequestMatcher.Builder(introspector);
//    }
//
//    @Bean
//    public SecurityFilterChain applicationSecurity(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .formLogin(AbstractHttpConfigurer::disable)
//                .exceptionHandling(h -> h.authenticationEntryPoint(unauthorizedHandler))
//                .securityMatcher(mvc.pattern("/**"))
//                .authorizeHttpRequests(registry -> registry
//                        .requestMatchers(mvc.pattern("/auth/login")).permitAll()
//                        .requestMatchers(mvc.pattern("/auth/register")).permitAll()
//                        .requestMatchers(mvc.pattern("/api/**")).permitAll()
//                        .requestMatchers(mvc.pattern("/v3/api-docs")).permitAll()
//                        .requestMatchers(mvc.pattern("/v3/api-docs/**")).permitAll()
//                        .requestMatchers(mvc.pattern("/swagger-resources/**")).permitAll()
//                        .requestMatchers(mvc.pattern("/configuration/**")).permitAll()
//                        .requestMatchers(mvc.pattern("/h2-console")).permitAll()
//                        .requestMatchers(mvc.pattern("h2-console/*")).permitAll()
//                        .requestMatchers(mvc.pattern("/swagger-ui/*")).permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
////        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        builder
//                .userDetailsService(customUserDetailService)
//                .passwordEncoder(passwordEncoder());
//        return builder.build();
//    }
//}

