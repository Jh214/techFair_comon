package techfair_comon.bg.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import techfair_comon.ResponseDto;
import techfair_comon.bg.repository.BgRepository;
import techfair_comon.bg.dto.CreateBgDto;
import techfair_comon.bg.dto.GetBgDto;
import techfair_comon.entity.Bg;

import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class BgServiceImpl implements BgService{
    private final BgRepository bgRepository;
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
            ErrorMessage.DataIntegrityViolationExceptionLog(e);
            return ResponseDto.setFailed("Bg 생성에 실패했습니다., 데이터 무결성 위반: " + e.getMessage());
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
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
    public ResponseDto<GetBgDto> getBgByBgId(Bg bg) {
        return bgRepository.findById(bg.getBgNo())
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
    public ResponseDto<Void> deleteBg(Bg bg) {
        /*
         * 여기에 현재 접속유저 == 관리자 || 게임작성자인지 확인하는 로직
         * */
        try {
            bgRepository.deleteById(bg.getBgNo());
            return ResponseDto.setSuccess("Bg가 성공적으로 삭제되었습니다.");
        } catch (EmptyResultDataAccessException e) {
            ErrorMessage.EmptyResultDataAccessExceptionLog(e);
            return ResponseDto.setFailed("BgNo 와 일치하는 Bg가 없습니다.");
        } catch (IllegalArgumentException e) {
            ErrorMessage.IllegalArgumentExceptionLog(e);
            return ResponseDto.setFailed("삭제 실패: 잘못된 BgNo가 전달되었습니다. 오류: " + e.getMessage());
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailed("Bg 삭제에 실패했습니다. 오류: " + e.getMessage());
        }
    }
}
