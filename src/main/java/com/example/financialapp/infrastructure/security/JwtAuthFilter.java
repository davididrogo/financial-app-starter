package com.example.financialapp.infrastructure.security;

import com.example.financialapp.infrastructure.persistence.jpa.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private final UserRepository repo;
    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.repo = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");

        if(auth != null && auth.startsWith("Bearer")){
            String token = auth.substring(7);
            try{
                var claims = jwtService.verify(token);
                String username = claims.getSubject();
                var user = repo.findByUsername(username).orElse(null);
                if(user != null){
                    var authorities = user.getRoles().stream()
                            .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)//TODO:validate
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    var authentication = new UsernamePasswordAuthenticationToken(
                            user.getId().toString(),
                            null,
                            authorities);
                    System.out.println("AUTH=" + authentication.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (Exception exception){
            //TODO: add exception
            }
        }
        filterChain.doFilter(request, response);
    }
}
