package dev.leeshuyun.Lifeguild.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * this filter catches all login requests to check for the presence of a jwt token
 * if there is no token, the request is rejected and returns a 403
 */
@Component
@RequiredArgsConstructor // creates contructor with any private final field
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // interface

    // @nonnull bc we require every one of these args
    // filterChain.doFilter calls the next filter we need in the chain
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, // requests to our server
            @NonNull HttpServletResponse response, // our server's response
            @NonNull FilterChain filterChain // chain of responsibility sequence of filter checks
    ) throws ServletException, IOException {

        // header is where our jwt "Bearer " tokens should be arriving in
        // header name usually is Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userid;

        // check for absence of jwt token in header for early return.
        // remb the space at the end, "Bearer " not "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // pass req and resp to next filter
            return;
        }
        // get token string after "Bearer "
        jwtToken = authHeader.substring(7);

        // now we extract userid from token
        userid = jwtService.extractUserid(jwtToken);

        // check if we need to authenticate user
        // if user authenticated, then pass straight to dispatcher servlet instead of
        // going thru this auth process
        // when SecurityContextHolder.getContext().getAuthentication() is null it means
        // user not yet authenticated
        if (userid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // fetch user details from database. can't rename it, but we're getting User
            // using the userid, not username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userid);
            // check if the token is valid or not
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // this authToken is needed to update security context.
                // we don't have creds so null
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userid,
                        null,
                        userDetails.getAuthorities());
                // give the authToken a few more details out of our http request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // now to finally update the security context holder with this authToken
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // now that user is authenticated, pass to next filter for processing
        filterChain.doFilter(request, response);
    }

}
