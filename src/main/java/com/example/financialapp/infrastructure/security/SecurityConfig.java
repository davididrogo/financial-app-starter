package com.example.financialapp.infrastructure.security;

import com.example.financialapp.infrastructure.persistence.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public UserDetailsService userDetailsService(UserRepository users) {
        return username -> users.findByUsername(username)
                .map(u -> org.springframework.security.core.userdetails.User
                        .withUsername(u.getUsername())
                        .password(u.getPasswordHash()) // must be encoded (BCrypt)
                        .authorities(//building authorities for security context
                                u.getRoles().stream()
                                        .map(r -> new SimpleGrantedAuthority(
                                                r.toString().startsWith("ROLE_") ? r.toString() : "ROLE_" + r))
                                        .toArray(SimpleGrantedAuthority[]::new)
                        )
                        .accountExpired(false)
                        .accountLocked(false)
                        .credentialsExpired(false)
                        //.disabled(!u.isEnabled())   // or false if you don’t track this
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService uds,
                                                         PasswordEncoder encoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationProvider authProvider) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll()
                    //TODO:for checking forbidden accounts open request , uncomment later
                /*.requestMatchers("/auth/**","/actuator/health", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/accounts/**").authenticated()*/
                .anyRequest().authenticated())
            //.httpBasic(Customizer.withDefaults());
                .authenticationProvider(authProvider)
                //.httpBasic(b -> b.disable())
                //.formLogin(f -> f.disable())
                //.addFilterBefore(new JwtAuthFilter(jwtService, userRepository),
                        //UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
