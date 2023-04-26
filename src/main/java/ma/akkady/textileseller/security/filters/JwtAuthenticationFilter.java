package ma.akkady.textileseller.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.akkady.textileseller.constants.SecurityConstants;
import ma.akkady.textileseller.dtos.VendorInfoDto;
import ma.akkady.textileseller.security.services.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("attemptAuthentication");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null; // Read the request body
        try {
            json = request.getReader().lines().collect(Collectors.joining());
            VendorInfoDto user = objectMapper.readValue(json, VendorInfoDto.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            log.error("AUTH Bad request {}", e.getMessage());
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication");
        //user info
        User user = (User) authResult.getPrincipal();
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // tokens generation
        String accessToken = tokenService.generateAccessToken(user.getUsername(), roles, request.getRequestURI());
        String refreshToken = tokenService.generateRefreshToken(user.getUsername(), request.getRequestURI());
        // http response
        response.setHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.BEARER_PREFIX + accessToken);
        response.setHeader("X-Refresh-token", refreshToken);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization,X-refresh-token");
        // if you want to sent the refresh token as a cookie
//        Cookie cookie = new Cookie("x-refresh-Token", refreshToken);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
    }
}