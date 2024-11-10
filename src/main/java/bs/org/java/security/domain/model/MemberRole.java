package bs.org.java.security.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "memberRole")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
