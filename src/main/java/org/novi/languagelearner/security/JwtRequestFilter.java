package org.novi.languagelearner.security;


import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: getAllStatsOfBert doesn't work, username is null when debugging  jwtService.extractUsername(jwt)
// TODO: push code to github
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader =
                httpServletRequest.getHeader("Authorization");

        String username = null;
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // jwt = delete 'Bearer' from the header and trim the rest of the string
            jwt = authorizationHeader.substring(7).trim();
            username = jwtService.extractUsername(jwt);
            roles = jwtService.extractSimpleGrantedAuthorities(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtService.validateToken(jwt)) {
                var usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(
                        username, null,
                        roles
                );
                usernamePasswordAuthenticationToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                // dit is een uitbreiding mocht je meer data in je token willen hebben en meer data willen doorgeven.

                 ApiUserDetails apiUserDetails = new ApiUserDetails(username,jwtService.extractRoles(jwt));
                 usernamePasswordAuthenticationToken.setDetails(apiUserDetails);
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }

}
