package bs.org.java.security.infrastructure.repository.service;

import bs.org.java.security.application.dto.SignInRequestDto;
import bs.org.java.security.application.dto.SignUpRequestDto;
import bs.org.java.security.application.dto.SignUpResponseDto;
import bs.org.java.security.application.service.MemberService;
import bs.org.java.security.domain.model.Member;
import bs.org.java.security.domain.model.MemberRole;
import bs.org.java.security.infrastructure.repository.MemberRepository;
import bs.org.java.security.util.JwtTokenProviderUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderUtil jwtTokenProvider;

    @Override
    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        Member member = Member.builder()
                .username(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .nickname(signUpRequestDto.getNickname())
                .build();

        MemberRole memberRole = MemberRole.builder()
                .role("ROLE_USER")
                .member(member)
                .build();

        member.getRoles().add(memberRole);

        memberRepository.save(member);
        return SignUpResponseDto.toDTO(member);
    }

    @Override
    @Transactional
    public String sign(SignInRequestDto signInRequestDto, HttpServletResponse httpServletResponse) {

        Member member = memberRepository.findByUsername(signInRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        // 비밀번호 일치 확인
        if (!passwordEncoder.matches(signInRequestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        List<String> roles = member.getRoles().stream().map(MemberRole::getRole).toList();

        // 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(member.getUsername(), roles);
        jwtTokenProvider.generateRefreshToken(member.getUsername());

        httpServletResponse.addHeader(JwtTokenProviderUtil.AUTHORIZATION_HEADER, accessToken);

        return accessToken;
    }
}
