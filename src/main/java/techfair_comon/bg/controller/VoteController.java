package techfair_comon.bg.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import techfair_comon.ResponseDto;
import techfair_comon.bg.dto.VoteDto;
import techfair_comon.bg.dto.VoteResultDto;
import techfair_comon.bg.service.VoteService;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;
import techfair_comon.entity.vote.VoteId;
import techfair_comon.entity.vote.VoteType;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {
    private final VoteService voteService;

    /* 해당 bgNo에 투표 */
    @PostMapping("/{bgNo}")
    public ResponseDto<Void> voteBg(@RequestBody @Valid VoteDto voteDto, @PathVariable("bgNo") Long bgNo, Authentication authentication) {
        voteDto.setBg(Bg.builder().bgNo(bgNo).build());
        voteDto.setUser(DecodeJwt.toUserNo(authentication));
        return voteService.saveVote(voteDto);
    }
    /* 모든 투표 결과 불러오기 */
    @GetMapping
    public ResponseDto<VoteResultDto> getVoteList() {
        return voteService.getVote(null);
    }
    /* 해당 유저가 투표한 bg의 모든 투표 결과 */
    @GetMapping("/user")
    public ResponseDto<VoteResultDto> getVoteByUserNo(Authentication authentication) {
        VoteDto voteDto = VoteDto.builder().user(DecodeJwt.toUserNo(authentication)).build();
        return voteService.getVote(voteDto);
    }
    /* 해당 bg의 투표 결과 */
    @GetMapping("/{bgNo}")
    public ResponseDto<VoteResultDto> getVoteByBgNo(@PathVariable("bgNo") Long bgNo) {
        VoteDto voteDto = VoteDto.builder().bg(Bg.builder().bgNo(bgNo).build()).build();
        return voteService.getVote(voteDto);
    }

    /* 해당 유저가 해당 bg에 투표한 타입 */
    @GetMapping("/voteType/{bgNo}")
    public ResponseDto<VoteType> getUserVoteType(@PathVariable("bgNo") Long bgNo, Authentication authentication) {
        VoteDto voteDto = VoteDto.builder()
                .user(DecodeJwt.toUserNo(authentication))
                .bg(Bg.builder().bgNo(bgNo).build())
                .build();
        return voteService.getVoteTypeInBgByUserNo(voteDto);
    }
    /* 해당 bgNo에 투표결과를 수정*/
    @PatchMapping("/{bgNo}")
    public ResponseDto<Void> updateVote(@PathVariable("bgNo") Long bgNo, @RequestBody VoteDto voteDto, Authentication authentication) {
        voteDto.setBg(Bg.builder().bgNo(bgNo).build());
        voteDto.setUser(DecodeJwt.toUserNo(authentication));
        return voteService.updateVote(voteDto);
    }

    /* 투표 삭제 */
    @DeleteMapping("/{bgNo}")
    public ResponseDto<Void> deleteVote(@PathVariable("bgNo") Long bgNo, Authentication authentication) {
        VoteDto voteDto = VoteDto.builder()
                .bg(Bg.builder()
                        .bgNo(bgNo)
                        .build())
                .user(DecodeJwt.toUserNo(authentication))
                .build();
        return voteService.deleteVote(voteDto);
    }
}
