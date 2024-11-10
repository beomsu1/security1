package bs.org.java.security.application.dto;

import bs.org.java.security.domain.model.Member;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SignUpResponseDto {

    private String username;
    private String nickname;
    private List<MemberRoleDto> roles;

    public static SignUpResponseDto toDTO(Member member){
        List<MemberRoleDto> roles = member.getRoles().stream().map(
                role -> new MemberRoleDto(role.getRole())).toList();

        return SignUpResponseDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .roles(roles)
                .build();
    }
}
