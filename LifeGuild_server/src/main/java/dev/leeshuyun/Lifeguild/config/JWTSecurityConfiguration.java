package dev.leeshuyun.Lifeguild.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// @configuration and @EnableWebSecurity needs to be together in Springboot3.0
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // injects final variables
public class JWTSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // configs all the HTTP security of our app.
    // Spring Security will look for this Bean SecurityFilterChain at startup
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf() // disable csrf verification
                .disable()
                .cors() // allow Access Control headers for Angular (CORS)
                .and()
                // get these whitelisted requests below to pass without authenticating
                // usually used for login and account creation bc user don't have token yet
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                // .requestMatchers(HttpMethod.GET, "/api/v1/auth/**")
                // .hasAuthority("SCOPE_read") // allows GET requests to Read
                // .requestMatchers(HttpMethod.POST, "/api/v1/auth/register")
                // .hasAuthority("SCOPE_write") // allows POST requests to Read+Write... NONE OF US CAN GET THIS TO WORK idk why
                // for the rest of the requests not stated above we need authentication
                .anyRequest()
                .authenticated()
                // specify using stateless session. Spring will create a new session for each
                // request
                // JWT tokens will be the one carrying the payload data to this centralised
                // backend server. this ensures that authentication is done for each request
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // specify the authentication provider bean I want to use (bean in
                // ApplicationConfig)
                .and()
                .authenticationProvider(authenticationProvider)
                // specify the use of the JWTAuthenticationFilter before the filter calls
                // UsernamePasswordAuthentication
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // if we were doing OAuth. specify that this is resource server, with jwt tokens
        // below
        // .and()
        // .oauth2ResourceServer()
        // .jwt();

        return http.build();
    }
}