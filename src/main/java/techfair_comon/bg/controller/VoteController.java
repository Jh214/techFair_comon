package techfair_comon.bg.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import techfair_comon.ResponseDto;
import techfair_comon.bg.dto.VoteDto;
import techfair_comon.bg.dto.VoteResultDto;
import techfair_comon.bg.service.VoteService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/{bgNo}") /*userId값 토큰에서 불러와서 넣기*/
    public ResponseDto<Void> voteBg(@RequestBody @Valid VoteDto voteDto, @PathVariable("bgNo") Long bgNo) {
        voteDto.setBgNo(bgNo);
        return voteService.saveVote(voteDto);
    }

    @GetMapping
    public ResponseDto<VoteResultDto> getVoteList() {
        return voteService.getVote(null);
    }

    @GetMapping("/user/{userNo}") /*userId값 토큰에서 불러와서 넣는거로 바꾸기*/
    public ResponseDto<VoteResultDto> getVoteByUserNo(@PathVariable("userNo") Long userNo) {
        VoteDto voteDto = VoteDto.builder().userNo(userNo).build();
        return voteService.getVote(voteDto);
    }

    @GetMapping("/bg/{bgNo}")
    public ResponseDto<VoteResultDto> getVoteByBgNo(@PathVariable("bgNo") Long bgNo) {
        VoteDto voteDto = VoteDto.builder().bgNo(bgNo).build();
        return voteService.getVote(voteDto);
    }

    @PatchMapping("/{bgNo}") /*userId값 토큰에서 불러와서 넣기*/
    public ResponseDto<Void> updateVote(@PathVariable("bgNo") Long bgNo, @RequestBody VoteDto voteDto) {
        voteDto.setBgNo(bgNo);
        return voteService.updateVote(voteDto);
    }

    @DeleteMapping("/{bgNo}") /*userId값 토큰에서 불러와서 넣기*/
    public ResponseDto<Void> deleteVote(@PathVariable("bgNo") Long bgNo) {
        VoteDto voteDto = VoteDto.builder().bgNo(bgNo).build();
        return voteService.deleteVote(voteDto);
    }
}
