package techfair_comon.bg.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TransactionRequiredException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import techfair_comon.ResponseDto;
import techfair_comon.bg.dto.VoteDto;
import techfair_comon.bg.dto.VoteResultDto;
import techfair_comon.bg.repository.VoteRepository;
import techfair_comon.entity.vote.Vote;
import techfair_comon.entity.vote.VoteType;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class VoteServiceImpl implements VoteService{
    private final VoteRepository voteRepository;
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<Void> saveVote(VoteDto voteDto) {
        Vote vote = voteDto.toEntity();
        try{
            voteRepository.save(vote);
            return ResponseDto.setSuccess("Vote 가 성공적으로 생성되었습니다.");
        } catch (DataIntegrityViolationException e) {
            ErrorMessage.DataIntegrityViolationExceptionLog(e);
            return ResponseDto.setFailed("Vote 생성에 실패했습니다., 데이터 무결성 위반: " + e.getMessage());
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailed("알수없는오류가 발생했습니다. 오류 : " + e.getMessage());
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<VoteResultDto> getVote(VoteDto voteDto) {
        try {
            if (voteDto == null) {
                List<Object[]> objects = voteRepository.countAllVotesByVoteTypeGrouped();
                VoteResultDto voteResultDto = voteResultDtoMapper(objects);
                return ResponseDto.setSuccessData("모든 vote 조회를 성공했습니다.", voteResultDto);
            } else if(voteDto.getUserNo() != null) {
                List<Object[]> votes = voteRepository.countVotesByVoteTypeGroupedByUser(voteDto.toEntity());
                VoteResultDto voteResultDto = voteResultDtoMapper(votes);
                return ResponseDto.setSuccessData("해당유저의 vote 조회를 성공했습니다.", voteResultDto);
            } else if(voteDto.getBgNo() != null) {
                    List<Object[]> votes = voteRepository.countVotesByVoteTypeGroupedByBg(voteDto.toEntity());
                    VoteResultDto voteResultDto = voteResultDtoMapper(votes);
                    return ResponseDto.setSuccessData("해당 Bg 의 vote 조회를 성공했습니다.", voteResultDto);
            }/* else {
                List<Object[]> objects = voteRepository.countAllVotesByVoteTypeGrouped();
                VoteResultDto voteResultDto = voteResultDtoMapper(objects);
                return ResponseDto.setSuccessData("모든 vote 조회를 성공했습니다.", voteResultDto);
            }*/else {
                return ResponseDto.setFailedData("유효한 파라미터가 없습니다.", null);
            }
        } catch (DataAccessException e) {
            ErrorMessage.DataAccessExceptionLog(e);
            return ResponseDto.setFailedData("데이터베이스 연결에 실패했습니다. 오류: " + e.getMessage(), null);
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailedData("알수없는오류가 발생했습니다. 오류 : " + e.getMessage(), null);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<VoteDto> getVoteTypeInBgByUserNo(VoteDto voteDto) {
        try {
            Optional<VoteType> voteType = voteRepository.findVotedByUserAndBg(voteDto.toEntity());
            if(voteType.isPresent()) {
                voteDto.setVoteType(voteType.get());
                return ResponseDto.setSuccessData("조회에 성공했습니다.", voteDto);
            } else {
                return ResponseDto.setSuccessData("조회된 데이터가 없습니다.", null);
            }
        } catch (DataAccessException e) {
            ErrorMessage.DataAccessExceptionLog(e);
            return ResponseDto.setFailedData("데이터베이스 연결에 실패했습니다. 오류: " + e.getMessage(), null);
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailedData("알수없는오류가 발생했습니다. 오류 : " + e.getMessage(), null);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public ResponseDto<Void> updateVote(VoteDto voteDto) {
        try {
            voteRepository.updateVoteTypeByUserAndBg(voteDto.toEntity());
            return ResponseDto.setSuccess("vote 업데이트에 성공했습니다.");
        } catch (DataAccessException e) {
            ErrorMessage.DataAccessExceptionLog(e);
            return ResponseDto.setFailed("데이터베이스 연결에 실패했습니다. 오류: " + e.getMessage());
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailed("알수없는오류가 발생했습니다. 오류 : " + e.getMessage());
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<Void> deleteVote(VoteDto voteDto) {
        try {
            voteRepository.deleteVoteByUserAndBg(voteDto.toEntity());
            return ResponseDto.setSuccess("vote 삭제에 성공했습니다.");
        } catch (EmptyResultDataAccessException e) {
            ErrorMessage.EmptyResultDataAccessExceptionLog(e);
            return ResponseDto.setFailed("일치하는 데이터가 없습니다. 오류: " + e.getMessage());
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailed("알수없는오류가 발생했습니다. 오류 : " + e.getMessage());
        }
    }


    private VoteResultDto voteResultDtoMapper(List<Object[]> votes) {
        VoteResultDto voteResultDto = new VoteResultDto();
        votes.forEach(vote -> {
            Long bgNo = (Long) vote[0];
            VoteType voteType = (VoteType) vote[1];
            Long count = (Long) vote[2];
            voteResultDto.addVote(bgNo, voteType, count);
        });
        return voteResultDto;
    }
}
