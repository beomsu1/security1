package bs.org.java.security.application.service;

import bs.org.java.security.application.dto.SignInRequestDto;
import bs.org.java.security.application.dto.SignUpRequestDto;
import bs.org.java.security.application.dto.SignUpResponseDto;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    SignUpResponseDto signUp (SignUpRequestDto signUpRequestDto);

    String sign(SignInRequestDto signInRequestDto, HttpServletResponse httpServletResponse);
}
