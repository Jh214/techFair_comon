package techfair_comon.bg.service;

import techfair_comon.ResponseDto;
import techfair_comon.bg.dto.BgDto;
import techfair_comon.bg.dto.CreateBgDto;
import techfair_comon.bg.dto.GetBgDto;
import techfair_comon.bg.dto.ReportDto;
import techfair_comon.entity.Bg;

import java.util.List;

public interface BgService {
    /**
     * 새 밸런스게임을(BG) 생성합니다.
     * @param createBgDto 게임정보를 담고있는 DTO 로,
     *                    null 값이아닌 bgRed, bgBlue, bgTitle 필드를 포함해야합니다.
     */
    ResponseDto<Void> createBg(CreateBgDto createBgDto);

    /**
     * 모든 밸런스게임(BG)의 정보를 반환합니다.
     * @return bgNo, bgRed, bgBlue, bgTitle, createdAt을 포함하는 GetBgDto 의 리스트
     */
    ResponseDto<List<GetBgDto>> getAllBgs();

    /**
     * 단일 밸런스게임을(Bg) 조회합니다.
     * @param bgDto BgDto 의 bgNo 값을 필요로 합니다.
     * @return bgNo, bgRed, bgBlue, bgTitle, createdAt을 포함하는 GetBgDto
     */
    ResponseDto<GetBgDto> getBgByBgId(BgDto bgDto);

    /**
     * 밸런스게임을(BG) 삭제합니다.
     * @param bgDto BgDto 의 bgNo, user 필드의 userNo 값을 필요로 합니다.
     */
    ResponseDto<Void> deleteBg(BgDto bgDto);

    /**
     * bg 를 신고합니다. 일정 신고수를 넘으면 bg 를 삭제시키거나, 관리자에게 알림을 줍니다
     * @param reportDto ReportDto 의 userNo 가포함된 User 객체, bgNo 가 포함된 Bg 객체를 필요로 합니다.
     */
    ResponseDto<Void> reportBg(ReportDto reportDto);
}
