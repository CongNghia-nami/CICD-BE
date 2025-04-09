package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security;


import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                // Mở CORS (xóa cors().disable())
                .cors() // Bật CORS
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                        .anyRequest().permitAll()
                );
                // .authorizeHttpRequests(registry -> registry
                //         .requestMatchers("/user/login").permitAll()
                //         .requestMatchers("/member/login").permitAll()
                //         .requestMatchers("/auth/login").permitAll()
                //         .requestMatchers("/user/register").hasAnyAuthority("ROLE_Admin", "ROLE_Staff")
                //         .requestMatchers("/api-public/lichsutapluyen/**").permitAll()
                //         .requestMatchers("/api/doanh-thu").hasAuthority("ROLE_Admin")
                //         .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                //         .requestMatchers("/**").hasAnyAuthority("ROLE_Admin", "ROLE_Staff") // Restrict all other endpoints to Admin and Staff
                //         .anyRequest().authenticated()
                // );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // @Bean
    // public WebMvcConfigurer corsConfigurer() {
    //     return new WebMvcConfigurer() {
    //         @Override
    //         public void addCorsMappings(CorsRegistry registry) {
    //             registry.addMapping("/**")
    //                     .allowedOrigins("http://3.106.38.243:3000")
    //                     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    //                     .allowedHeaders("*")
    //                     .allowCredentials(true);//cho phep client gửi thông tin xac thuc tuong tu nhu cookie or session
    //         }
    //     };
    // }
       @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("https://gymcity.nghia2801dt.fun") // cho phép React FE gọi
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

}
