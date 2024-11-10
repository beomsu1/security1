package bs.org.java.security.application.dto;

import bs.org.java.security.domain.model.Member;
import bs.org.java.security.domain.model.MemberRole;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter

public class MemberDto extends User {

    private String nickname;
    private List<String> roles = new ArrayList<>();

    public MemberDto(String username, String password, String nickname, List<String> roles) {
        super(username, password, roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        this.nickname = nickname;
        this.roles = roles;
    }

    public static Member toEntity(MemberDto memberDTO){
        List<MemberRole> roles = memberDTO.getRoles().stream()
                .map(roleName -> new MemberRole(null, roleName, null))
                .collect(Collectors.toList());

        return Member.builder()
                .username(memberDTO.getUsername())
                .password(memberDTO.getPassword())
                .nickname(memberDTO.getNickname())
                .roles(roles)
                .build();
    }
}
