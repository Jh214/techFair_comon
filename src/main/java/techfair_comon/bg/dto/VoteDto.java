package techfair_comon.bg.dto;

import lombok.Getter;
import lombok.Setter;
import techfair_comon.entity.vote.VoteType;

@Getter @Setter
public class VoteDto {
    private Long userNo;
    private Long bgNo;
    private VoteType voteType;
}
