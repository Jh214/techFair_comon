package techfair_comon.bg.dto;

import lombok.Getter;
import lombok.Setter;
import techfair_comon.entity.vote.Vote;
import techfair_comon.entity.vote.VoteId;
import techfair_comon.entity.vote.VoteType;

@Getter @Setter
public class VoteDto {
    private Long userNo;
    private Long bgNo;
    private VoteType voteType;

    public static VoteDto fromEntity(Vote vote) {
        VoteDto dto = new VoteDto();
        dto.setUserNo(vote.getId().getUserNo());
        dto.setBgNo(vote.getId().getBgNo());
        dto.setVoteType(vote.getVoted());
        return dto;
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
