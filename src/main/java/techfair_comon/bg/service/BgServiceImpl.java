package techfair_comon.bg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import techfair_comon.ResponseDto;
import techfair_comon.bg.dto.BgDto;
import techfair_comon.bg.dto.ReportDto;
import techfair_comon.bg.repository.BgRepository;
import techfair_comon.bg.dto.CreateBgDto;
import techfair_comon.bg.dto.GetBgDto;
import techfair_comon.bg.repository.ReportRepository;
import techfair_comon.entity.Bg;
import techfair_comon.entity.report.Report;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class BgServiceImpl implements BgService{
    private final BgRepository bgRepository;
    private final ReportRepository reportRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<Void> createBg(CreateBgDto createBgDto) {
        Bg bg = createBgDto.toEntity();
        try {
            Bg save = bgRepository.save(bg);
            return ResponseDto.setSuccess("Bg가 성공적으로 생성되었습니다.");
        } catch (DataIntegrityViolationException e) {
            return ResponseDto.setFailed("Bg 생성에 실패했습니다., 데이터 무결성 위반: " + e.getMessage());
        } catch (Exception e) {
            return ResponseDto.setFailed("Bg 생성에 실패했습니다. 오류: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<List<GetBgDto>> getAllBgs() {
        List<Bg> bgs = bgRepository.findAll();
        if(bgs.isEmpty()) {
            return ResponseDto.setSuccessData("조회된 데이터가 없습니다.", Collections.emptyList());
        } else {
            List<GetBgDto> collect = bgs.stream()
                    .map(GetBgDto::fromEntity)
                    .toList();
            return ResponseDto.setSuccessData("Bg 조회에 성공했습니다.", collect);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<GetBgDto> getBgByBgId(BgDto bgDto) {
        return bgRepository.findById(bgDto.toEntity().getBgNo())
                .map(bg1 -> {
                    GetBgDto getBgDto = GetBgDto.fromEntity(bg1);
                    return ResponseDto.setSuccessData("Bg 조회에 성공했습니다.", getBgDto);
                })
                .orElseGet(() -> ResponseDto.setFailedData("Bg 조회에 실패했습니다.", null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<Void> deleteBg(BgDto bgDto) {
        Bg bg = bgDto.toEntity();
        Long userNoByBgNo = bgRepository.findUserNoByBgNo(bg.getBgNo());
        if(userNoByBgNo == null) {
            return ResponseDto.setFailed("BgNo 와 일치하는 Bg가 없습니다.");
        }
        if(bg.getUser().getUserNo() == 0 /*관리자 번호*/ || bg.getUser().getUserNo().equals(userNoByBgNo)) {
            try {
                bgRepository.deleteById(bg.getBgNo());
                return ResponseDto.setSuccess("Bg가 성공적으로 삭제되었습니다.");
            } catch (Exception e) {
                return ResponseDto.setFailed("Bg 삭제에 실패했습니다. 오류: " + e.getMessage());
            }
        } else {
            return ResponseDto.setFailed("삭제할수 있는 권한이 없습니다.");
        }
    }

    @Override
    public ResponseDto<Void> reportBg(ReportDto reportDto) {
        Report report = reportDto.toEntity();
        reportRepository.save(report);
        if(reportRepository.countReportByBgNo(report) >= 2) {
            bgRepository.deleteById(report.getBg().getBgNo());
            return ResponseDto.setSuccess("신고수가 일정수준을 넘어 bg 가 삭제되었습니다.");
        } else {
            return ResponseDto.setSuccess("성공적으로 처리되었습니다.");
        }
    }


}
