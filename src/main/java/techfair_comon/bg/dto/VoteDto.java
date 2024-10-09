package techfair_comon.bg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;
import techfair_comon.entity.vote.Vote;
import techfair_comon.entity.vote.VoteId;
import techfair_comon.entity.vote.VoteType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private Long userNo;
    private Long bgNo;
    private User user;
    private Bg bg;

    @NotNull(message = "voteType cannot be null")
    private VoteType voteType;

    public static VoteDto fromEntity(Vote vote) {
        return VoteDto.builder()
                .voteType(vote.getVoteType())
                .user(vote.getUser())
                .bg(vote.getBg())
                .build();
    }

    public Vote toEntity() {
        return Vote.builder()
                .id(VoteId.builder().
                        userNo(this.userNo)
                        .bgNo(this.bgNo)
                        .build())
                .voteType(this.voteType)
                .bg(this.bg)
                .user(this.user)
                .build();

    }
}
