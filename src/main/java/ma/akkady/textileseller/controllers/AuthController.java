package ma.akkady.textileseller.controllers;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.akkady.textileseller.constants.MappingUrls;
import ma.akkady.textileseller.entities.SecurityRole;
import ma.akkady.textileseller.entities.Vendor;
import ma.akkady.textileseller.security.services.TokenService;
import ma.akkady.textileseller.services.AccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ma.akkady.textileseller.constants.SecurityConstants.BEARER_PREFIX;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final AccountService accountService;

    @GetMapping(MappingUrls.Auth.REFRESH)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("REQUEST refresh token");
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasLength(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
            try {
                String token = authHeader.substring(BEARER_PREFIX.length());
                DecodedJWT decodedJWT = tokenService.decodeToken(token);
                String username = decodedJWT.getSubject();
                Vendor user = accountService.loadUserByUsername(username);
                List<String> roles = user.getRoles().stream().map(SecurityRole::getRoleName).collect(Collectors.toList());
                String accessToken = tokenService.generateAccessToken(username, roles, request.getRequestURI());
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error-message", e.getMessage());
            }
        }
    }
}