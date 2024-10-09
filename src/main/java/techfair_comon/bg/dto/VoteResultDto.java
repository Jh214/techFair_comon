package techfair_comon.bg.dto;

import lombok.Getter;
import lombok.Setter;
import techfair_comon.entity.vote.VoteType;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class VoteResultDto {
    private List<VoteCountDto> votes;

    public VoteResultDto() {
        this.votes = new ArrayList<>();
    }

    public void addVote(Long bgNo, VoteType voteType, Long count) {
        votes.add(new VoteCountDto(bgNo, voteType, count));
    }

    @Getter @Setter
    public static class VoteCountDto {
        private Long bgNo;
        private VoteType voteType;
        private Long count;

        public VoteCountDto(Long bgNo, VoteType voteType, Long count) {
            this.bgNo = bgNo;
            this.voteType = voteType;
            this.count = count;
        }
    }
}
