package techfair_comon.bg.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import techfair_comon.ResponseDto;
import techfair_comon.bg.dto.VoteDto;
import techfair_comon.bg.service.BgService;
import techfair_comon.bg.dto.CreateBgDto;
import techfair_comon.bg.dto.GetBgDto;
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

    @PostMapping("/create") /*0924 userId 저장하는부분 미구현*/
    public ResponseDto<Void> createBg(@RequestBody @Valid CreateBgDto createBgDto) {
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

    @DeleteMapping("/{bgNo}") /*0924 현재접속 userId로 삭제권한 부여 미구현*/
    public ResponseDto<Void> deleteBg(@PathVariable("bgNo") Long bgNo) {
        Bg bg = Bg.builder().bgNo(bgNo).build();
        return bgService.deleteBg(bg);
    }

    /*@PostMapping("/vote")
    public ResponseDto<Void> voteBg(@RequestBody @Valid VoteDto voteDto) {

    }*/
}
