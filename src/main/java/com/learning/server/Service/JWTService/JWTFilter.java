package com.learning.server.Service.JWTService;

import com.learning.server.Service.ServiceUserFinder.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    private ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username =null;

        try {
            if(authHeader!=null && authHeader.startsWith("Bearer"))
            {
                token= authHeader.substring(7);
                username = jwtService.extractUserName(token);
            }
            if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UserDetails userDetail = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
                if(jwtService.validateToken(token, userDetail))
                {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetail,null, userDetail.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            setErrorResponse(response,"Invalid token signature");
        }
        catch (ExpiredJwtException e)
        {
            setErrorResponse(response,"Token has expired, please log in again");
        }
        catch (JwtException e)
        {
            setErrorResponse(response, "Invalid JWT Token");
        }
    }
    // Custom method to set the error response with a JSON message
    private void setErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String jsonResponse = "{\"error\": \"" + message + "\"}";
        response.getWriter().write(jsonResponse);
    }
}
