package ma.akkady.textileseller.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.constants.MappingUrls;
import ma.akkady.textileseller.security.services.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static ma.akkady.textileseller.constants.SecurityConstants.BEARER_PREFIX;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (hasToken(authHeader)) {
            try {
                String token = authHeader.substring(BEARER_PREFIX.length());
                DecodedJWT decodedJWT = tokenService.decodeToken(token);
                if (!request.getServletPath().equals(MappingUrls.Auth.REFRESH)) {
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<GrantedAuthority> authorities = Arrays.stream(roles)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private boolean hasToken(String authHeader) {
        return StringUtils.hasLength(authHeader) && authHeader.startsWith(BEARER_PREFIX);
    }
}
