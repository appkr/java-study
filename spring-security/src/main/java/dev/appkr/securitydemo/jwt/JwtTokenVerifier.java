package dev.appkr.securitydemo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenVerifier extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");
    if (Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String secretKey = "supersecurekeysupersecurekeysupersecurekeysupersecurekeysupersecurekeysupersecurekey";
    try {
      String token = authorizationHeader.replace("Bearer ", "");
      final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
          .parseClaimsJws(token);

      final Claims body = claimsJws.getBody();
      final String username = body.getSubject();
      final List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
      final Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
          .map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());
      final Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (JwtException e) {
      throw new IllegalStateException("Token cannot be trusted");
    }

    filterChain.doFilter(request, response);
  }
}
