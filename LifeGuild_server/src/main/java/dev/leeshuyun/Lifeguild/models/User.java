package dev.leeshuyun.Lifeguild.models;

import java.util.Collection;
import java.util.List;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
// public class User implements UserDetails {
public class User {
    private int userid;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String userpassword;
    private Role userrole;
    private int confirmationcode;
    private Boolean isemailconfirmed; // for non-google login users
    private Boolean isgooglelogin;
    private String telegramchatid;

    // @Override
    // public String toString() {
    // return "User [userid= %s firstname=%s lastname=%s, email=%s, username=%s ,
    // userpassword=%s, userrole=%s, googlelogin=%s]"
    // .formatted(userid, firstname, lastname, email, username, userpassword,
    // userrole.name(),
    // isgooglelogin);
    // }

    // Authorities = role of the user. eg. Player? Admin?
    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // // userrole PLAYER, MODERATOR, ADMIN
    // return List.of(new SimpleGrantedAuthority(userrole.name()));
    // }

    // @Override
    // public String getPassword() {
    // return userpassword;
    // }

    // @Override
    // public boolean isAccountNonExpired() {
    // return true;
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    // return true;
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    // return true;
    // }

    // @Override
    // public boolean isEnabled() {
    // return true;
    // }

}
