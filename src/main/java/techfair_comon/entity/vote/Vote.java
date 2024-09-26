package techfair_comon.entity.vote;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;

@Entity
@Table(name = "vote")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Vote {

    @EmbeddedId
    private VoteId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "voted", nullable = false)
    private VoteType voted;

    @MapsId("userNo")
    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @MapsId("bgNo")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bg_no", insertable = false, updatable = false)
    private Bg bg;

}

