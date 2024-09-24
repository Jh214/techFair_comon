package techfair_comon.entity.vote;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;

@Entity
@Table(name = "vote")
@IdClass(VoteId.class)  // 복합 키 사용
@Getter @Setter
public class Vote {

    @Id
    @Column(name = "user_no") // User의 userNo를 직접 사용
    private Long userNo;

    @Id
    @Column(name = "bg_no") // Bg의 bgNo를 직접 사용
    private Long bgNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "voted", nullable = false)
    private VoteType voted;

    // 관계를 표현하는 필드
    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false) // FK 관계
    private User user;

    @ManyToOne
    @JoinColumn(name = "bg_no", insertable = false, updatable = false) // FK 관계
    private Bg bg;
}

