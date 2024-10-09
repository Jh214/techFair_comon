package techfair_comon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import techfair_comon.entity.comment.Comment;
import techfair_comon.entity.vote.Vote;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "bg")
@Getter @Setter
//빌더, 전체를 인자로 받는 생성자, 인자를 받지 않는 생성자를 자동생성해주는 어노테이션 추가
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bgNo;
    @Column(name = "bg_dec", columnDefinition = "INT DEFAULT 0")
    private int bgDec;
    @Column(name = "bg_red", nullable = false)
    private String bgRed;
    @Column(name = "bg_blue", nullable = false)
    private String bgBlue;
    @Column(name = "bg_title", nullable = false)
    private String bgTitle;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    /*userNo (작성자 필요)*/

//  user 엔티티 join
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userNo")
    private User user;

//  comment 엔티티 join
    @OneToMany(mappedBy = "bg", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @OneToMany(mappedBy = "bg", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> vote;

}
