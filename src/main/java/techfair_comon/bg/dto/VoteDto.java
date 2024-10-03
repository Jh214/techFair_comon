package techfair_comon.bg.dto;

import lombok.Builder;
import lombok.Data;
import techfair_comon.entity.vote.Vote;
import techfair_comon.entity.vote.VoteId;
import techfair_comon.entity.vote.VoteType;

@Data
@Builder
public class VoteDto {
    private Long userNo;
    private Long bgNo;
    private VoteType voteType;

    public static VoteDto fromEntity(Vote vote) {
        return VoteDto.builder()
                .userNo(vote.getId().getUserNo())
                .bgNo(vote.getId().getBgNo())
                .voteType(vote.getVoted())
                .build();
    }

    public Vote toEntity() {
        return Vote.builder()
                .id(VoteId.builder().
                        userNo(this.userNo)
                        .bgNo(this.bgNo)
                        .build())
                .voted(this.voteType)
                .build();

    }
}
