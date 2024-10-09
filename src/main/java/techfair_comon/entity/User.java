package techfair_comon.entity;

import jakarta.persistence.*;
import lombok.*;
import techfair_comon.entity.comment.Comment;
import techfair_comon.entity.vote.Vote;
import techfair_comon.user.enums.Grade;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no") // 사용자 닉네임
    private Long userNo;

    @Column(name = "user_id", unique = true)
    private String userId; // 사용자 ID (로그인용)

    @Column(name = "user_pw", nullable = false)
    private String userPw; // 비밀번호

    @Column(name = "user_name", nullable = false)
    private String userName; // 사용자 이름

    @Column(name = "user_tel", nullable = false)
    private String userTel; // 사용자 전화번호

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade role;

//    Bg엔티티 join
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bg> bg;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> vote;

}
