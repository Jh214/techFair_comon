package techfair_comon.entity.vote;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class VoteId implements Serializable {


    @Column(name = "user_no")
    private Long userNo;
    @Column(name = "bg_no")
    private Long bgNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteId)) return false;
        VoteId voteId = (VoteId) o;
        return Objects.equals(userNo, voteId.userNo) &&
                Objects.equals(bgNo, voteId.bgNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo, bgNo);
    }
}
