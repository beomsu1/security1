package bs.org.java.security.application.dto;

import lombok.Data;

@Data
public class SignInRequestDto {

    private String username;
    private String password;
}
