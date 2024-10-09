package techfair_comon.bg.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import techfair_comon.ResponseDto;
import techfair_comon.bg.service.BgService;
import techfair_comon.bg.dto.CreateBgDto;
import techfair_comon.bg.dto.GetBgDto;
import techfair_comon.bg.service.VoteService;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;

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

    @PostMapping("/create")
    public ResponseDto<Void> createBg(@RequestBody @Valid CreateBgDto createBgDto, Authentication authentication) {
        createBgDto.setUser(DecodeJwt.toUserNo(authentication));
        return bgService.createBg(createBgDto);
    }

    @GetMapping
    public ResponseDto<List<GetBgDto>> getBgList() {
        return bgService.getAllBgs();
    }

    @GetMapping("/{bgNo}")
    public ResponseDto<GetBgDto> getBgById(@PathVariable("bgNo") Long bgNo) {
        Bg bg = Bg.builder().bgNo(bgNo).build();
        return bgService.getBgByBgId(bg);
    }

    @DeleteMapping("/{bgNo}")
    public ResponseDto<Void> deleteBg(@PathVariable("bgNo") Long bgNo, Authentication authentication) {
        Bg bg = Bg.builder()
                .bgNo(bgNo)
                .user(DecodeJwt.toUserNo(authentication))
                .build();
        return bgService.deleteBg(bg);
    }

}
