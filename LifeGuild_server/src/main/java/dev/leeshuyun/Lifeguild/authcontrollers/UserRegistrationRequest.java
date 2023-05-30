package dev.leeshuyun.Lifeguild.authcontrollers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String userrole;
}
