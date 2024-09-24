package techfair_comon.entity.vote;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class VoteId implements Serializable {
    private Long userNo; // User ID
    private Long bgNo;   // Bg ID

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
