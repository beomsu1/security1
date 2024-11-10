package bs.org.java.security.infrastructure.security;

import bs.org.java.security.application.dto.MemberDto;
import bs.org.java.security.domain.model.Member;
import bs.org.java.security.domain.model.MemberRole;
import bs.org.java.security.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않은 username 입니다."));

        List<String> roles = member.getRoles().stream().map(MemberRole::getRole).toList();

        // MemberDto 생성 시 권한 목록 포함
        MemberDto memberDto = new MemberDto(member.getUsername(), member.getPassword(), member.getNickname(), roles);

        return memberDto;
    }
}
