package ma.akkady.textileseller.configs;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.constants.MappingUrls;
import ma.akkady.textileseller.security.filters.JwtAuthenticationFilter;
import ma.akkady.textileseller.security.filters.JwtAuthorizationFilter;
import ma.akkady.textileseller.security.services.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenService tokenService;
    private final AuthenticationConfiguration authenticationConfiguration;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions().disable())
                .cors().and()
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilter(jwtAuthenticationFilter())
                .addFilterBefore(new JwtAuthorizationFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth.antMatchers("/h2-console/**", MappingUrls.Auth.BASE + "/**", MappingUrls.VENDORS.BASE_URL + MappingUrls.VENDORS.REGISTRATION+"/**","/swagger-u/**").permitAll())
                .authorizeRequests(auth -> auth.antMatchers( MappingUrls.VENDORS.BASE_URL + "/**").hasAnyAuthority("ADMIN"))
                .authorizeRequests(auth -> auth.antMatchers(HttpMethod.GET, MappingUrls.API_URL+"/**").hasAnyAuthority("USER"))
                .authorizeRequests(auth -> auth.anyRequest().authenticated())
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> {
                    // Return an HTTP 401 Unauthorized response with a custom error message
                    response.setHeader("WWW-Authenticate", "Basic realm=\"Access to /auth/login authentication endpoint\"");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{ \"error\": \" Invalid credentials \"" + e.getMessage() + " }");
                })
                .accessDeniedHandler((request, response, e) -> {
                    // Return an HTTP 403 Forbidden response with a custom error message
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{ \"error\": \"" + e.getMessage() + " You are not allowed to see this resource.\" }");
                });
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuth = new JwtAuthenticationFilter(authenticationManager(), tokenService);
        jwtAuth.setFilterProcessesUrl(MappingUrls.Auth.LOGIN);
        return jwtAuth;
    }


}

