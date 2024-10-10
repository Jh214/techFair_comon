package techfair_comon.bg.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import techfair_comon.ResponseDto;
import techfair_comon.bg.dto.*;
import techfair_comon.bg.service.BgService;
import techfair_comon.bg.service.VoteService;
import techfair_comon.entity.Bg;

import java.util.List;

/**
 * 반환값이 없는 모든 엔드포인트는 성공 status: 200, 실패 status: 400 으로 반환됩니다.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/bg")
public class BgController {
    private final BgService bgService;
    private final VoteService voteService;

    /* bg 생성 */
    @PostMapping("/create")
    public ResponseDto<Void> createBg(@RequestBody @Valid CreateBgDto createBgDto, Authentication authentication) {
        createBgDto.setUser(DecodeJwt.toUserNo(authentication));
        return bgService.createBg(createBgDto);
    }
    /* 모든 bg 조회*/
    @GetMapping
    public ResponseDto<List<GetBgDto>> getBgList() {
        return bgService.getAllBgs();
    }
    /* 해당 bgNo 에맞는 bg 조회*/
    @GetMapping("/{bgNo}")
    public ResponseDto<GetBgDto> getBgById(@PathVariable("bgNo") Long bgNo) {
        BgDto bgDto = BgDto.builder().bgNo(bgNo).build();
        return bgService.getBgByBgId(bgDto);
    }
    /* 해당 bgNo 에맞는 bg 삭제*/
    @DeleteMapping("/{bgNo}")
    public ResponseDto<Void> deleteBg(@PathVariable("bgNo") Long bgNo, Authentication authentication) {
        BgDto bgDto = BgDto.builder()
                .bgNo(bgNo)
                .user(DecodeJwt.toUserNo(authentication))
                .build();
        return bgService.deleteBg(bgDto);
    }

    /* 해당 bgNo 신고 */
    @PatchMapping("/report/{bgNo}")
    public ResponseDto<Void> reportBg(@PathVariable("bgNo") Long bgNo, Authentication authentication) {
        ReportDto reportDto = ReportDto.builder().bg(Bg.builder().bgNo(bgNo).build()).user(DecodeJwt.toUserNo(authentication)).build();
        return bgService.reportBg(reportDto);
    }

}
