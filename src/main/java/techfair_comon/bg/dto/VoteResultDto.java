package techfair_comon.bg.dto;

import lombok.Getter;
import lombok.Setter;
import techfair_comon.entity.vote.VoteType;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class VoteResultDto {
    private Map<Long, Map<VoteType, Long>> voteTypes;
    public VoteResultDto() {
        this.voteTypes = new HashMap<>();
    }

    public void addVote(Long bgNo, VoteType voteType, Long count) {
        voteTypes.computeIfAbsent(bgNo, x -> new HashMap<>()).put(voteType, count);
    }
}
