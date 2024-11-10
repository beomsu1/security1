package bs.org.java.security.presentation.controller;

import bs.org.java.security.application.dto.SignInRequestDto;
import bs.org.java.security.application.dto.SignUpRequestDto;
import bs.org.java.security.application.dto.SignUpResponseDto;
import bs.org.java.security.application.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        SignUpResponseDto dto = memberService.signUp(signUpRequestDto);
        return ResponseEntity.ok(dto);
    }


    // 로그인
    @PostMapping("/sign")
    public ResponseEntity<String> sign(@RequestBody SignInRequestDto signInRequestDto, HttpServletResponse response){

        return ResponseEntity.ok(memberService.sign(signInRequestDto, response));
    }
    
}
