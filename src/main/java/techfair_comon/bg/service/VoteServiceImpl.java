package techfair_comon.bg.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
            return ResponseDto.setFailed("Vote 생성에 실패했습니다., 데이터 무결성 위반");
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailed("알수없는오류가 발생했습니다.");
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
            } else if(voteDto.getUser() != null) {
                List<Object[]> votes = voteRepository.countVotesByVoteTypeGroupedByUser(voteDto.toEntity());
                VoteResultDto voteResultDto = voteResultDtoMapper(votes);
                return ResponseDto.setSuccessData("해당유저가 투표한 bg의 vote 조회를 성공했습니다.", voteResultDto);
            } else if(voteDto.getBg() != null) {
                    List<Object[]> votes = voteRepository.countVotesByVoteTypeGroupedByBg(voteDto.toEntity());
                    VoteResultDto voteResultDto = voteResultDtoMapper(votes);
                    return ResponseDto.setSuccessData("해당 Bg 의 vote 조회를 성공했습니다.", voteResultDto);
            } else {
                return ResponseDto.setFailedData("유효한 파라미터가 없습니다.", null);
            }
        } catch (DataAccessException e) {
            ErrorMessage.DataAccessExceptionLog(e);
            return ResponseDto.setFailedData("데이터베이스 연결에 실패했습니다." , null);
        }  catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailedData("알수없는오류가 발생했습니다.", null);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<VoteType> getVoteTypeInBgByUserNo(VoteDto voteDto) {
        try {
            Optional<VoteType> voteType = voteRepository.findVotedByUserAndBg(voteDto.toEntity());
            if(voteType.isPresent()) {
                return ResponseDto.setSuccessData("조회에 성공했습니다.", voteType.get());
            } else {
                return ResponseDto.setSuccessData("조회된 데이터가 없습니다.", null);
            }
        } catch (DataAccessException e) {
            ErrorMessage.DataAccessExceptionLog(e);
            return ResponseDto.setFailedData("데이터베이스 연결에 실패했습니다.", null);
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailedData("알수없는오류가 발생했습니다.", null);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<Void> updateVote(VoteDto voteDto) {
        try {

            if(voteRepository.updateVoteTypeByUserAndBg(voteDto.toEntity()) == 1) {
                return ResponseDto.setSuccess("vote 업데이트에 성공했습니다.");
            } else {
                return ResponseDto.setFailed("해당하는 vote 가 없습니다.");
            }
        } catch (DataAccessException e) {
            ErrorMessage.DataAccessExceptionLog(e);
            return ResponseDto.setFailed("데이터베이스 연결에 실패했습니다.");
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailed("알수없는오류가 발생했습니다.");
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<Void> deleteVote(VoteDto voteDto) {
        try {
            int i = voteRepository.authByUserNo(voteDto.toEntity());
            if(i > 0) {
                voteRepository.deleteVoteByUserAndBg(voteDto.toEntity());
                return ResponseDto.setSuccess("vote 삭제에 성공했습니다.");
            } else {
                return ResponseDto.setFailed("삭제 권한이 없습니다.");
            }
        } catch (EmptyResultDataAccessException e) {
            ErrorMessage.EmptyResultDataAccessExceptionLog(e);
            return ResponseDto.setFailed("일치하는 데이터가 없습니다.");
        } catch (Exception e) {
            ErrorMessage.ExceptionLog(e);
            return ResponseDto.setFailed("알수없는오류가 발생했습니다.");
        }
    }


    private VoteResultDto voteResultDtoMapper(List<Object[]> votes) {
        VoteResultDto voteResultDto = new VoteResultDto();
        votes.forEach(vote -> {
            Long bgNo = (Long) vote[0];
            String voteTypeString = (String) vote[1];
            VoteType voteType = VoteType.valueOf(voteTypeString);
            Long count = (Long) vote[2];
            voteResultDto.addVote(bgNo, voteType, count);
        });
        return voteResultDto;
    }
}
