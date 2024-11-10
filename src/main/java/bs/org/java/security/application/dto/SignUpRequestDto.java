package bs.org.java.security.application.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String username;
    private String password;
    private String nickname;
}
