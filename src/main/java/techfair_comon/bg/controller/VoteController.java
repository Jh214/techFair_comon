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

@RestController
@AllArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/{bgNo}")
    public ResponseDto<Void> voteBg(@RequestBody @Valid VoteDto voteDto, @PathVariable("bgNo") Long bgNo, Authentication authentication) {
        voteDto.setBg(Bg.builder().bgNo(bgNo).build());
        voteDto.setUser(User.builder().userNo(DecodeJwt.toUserNo(authentication).getUserNo()).build());
        return voteService.saveVote(voteDto);
    }

    @GetMapping
    public ResponseDto<VoteResultDto> getVoteList() {
        return voteService.getVote(null);
    }

    @GetMapping("/user") /*userId값 토큰에서 불러와서 넣는거로 바꾸기*/
    public ResponseDto<VoteResultDto> getVoteByUserNo(Authentication authentication) {
        User user = DecodeJwt.toUserNo(authentication);
        VoteDto voteDto = VoteDto.builder().user(user).build();
        return voteService.getVote(voteDto);
    }

    @GetMapping("/bg/{bgNo}")
    public ResponseDto<VoteResultDto> getVoteByBgNo(@PathVariable("bgNo") Long bgNo) {
        VoteDto voteDto = VoteDto.builder().bg(Bg.builder().bgNo(bgNo).build()).build();
        return voteService.getVote(voteDto);
    }

    @PatchMapping("/{bgNo}") /*userId값 토큰에서 불러와서 넣기*/
    public ResponseDto<Void> updateVote(@PathVariable("bgNo") Long bgNo, @RequestBody VoteDto voteDto, Authentication authentication) {
        voteDto.setBg(Bg.builder().bgNo(bgNo).build());
        voteDto.setUser(DecodeJwt.toUserNo(authentication));
        return voteService.updateVote(voteDto);
    }
//
//    @DeleteMapping("/{bgNo}") /*userId값 토큰에서 불러와서 넣기*/
//    public ResponseDto<Void> deleteVote(@PathVariable("bgNo") Long bgNo) {
//        VoteDto voteDto = VoteDto.builder().bgNo(bgNo).build();
//        return voteService.deleteVote(voteDto);
//    }
}
