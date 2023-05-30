package dev.leeshuyun.Lifeguild.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.leeshuyun.Lifeguild.repositories.AuthRepository;
// import dev.leeshuyun.Lifeguild.repository.SQLUserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  // private final SQLUserRepository respository;
  private final AuthRepository respository;

  @Bean
  public UserDetailsService userDetailsService() {
    // we need to get the user from the database
    // returns an Optional<User> so throw an exception for Optional.empty
    return username -> respository.getUserByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

  };

  /*
   * this is the DAO responsible for fetching user details, encode password, etc
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    // specify a certain user details service out of many to use for fetching info
    // about user. there can be several user details service,
    // eg. one for sql DB, one for in-memory DB, one for different profile User
    authProvider.setUserDetailsService(userDetailsService());
    // specify the encoding algo
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /*
   * AuthManager is a bunch of methods.
   * Helps us authenticate using username and password
   */

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
