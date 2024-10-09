package techfair_comon.bg.service;

import techfair_comon.ResponseDto;
import techfair_comon.bg.dto.VoteDto;
import techfair_comon.bg.dto.VoteResultDto;

public interface VoteService {
    /**
     * @param voteDto 투표정보를 담고있는 DTO 로,
     *                null 값이아닌 userNo, bgNo, voteType 필드를 포함해야합니다.
     */
    ResponseDto<Void> saveVote(VoteDto voteDto);

    /**
     * @param voteDto userNo 또는 bgNo 를 선택적으로 담고있는 DTO 로,
     *                <pre>bgNo 값이 있을경우 해당 bg 의 VoteResultDto 를 리턴합니다.</pre>
     *                <pre>userNo 값이 있을경우 해당 userNo 의 VoteResultDto 를 리턴합니다.</pre>
     *                <pre>bgNo, userNo 값이 null 일경우, 모든 데이터의 VoteResultDto 를 리턴합니다.</pre>
     * @return 이와 같은 형식의 배열이 리턴됩니다.
     * { "votes": { "1": { "RED": 15, "BLUE": 10 }, "2": { "RED": 20 "BLUE": 10 } } }
     */
    ResponseDto<VoteResultDto> getVote(VoteDto voteDto);

//    /**
//     *
//     * @param voteDto null 값이 아닌 userNo, bgNo 을 포함해야합니다.
//     * @return 현재 접속유저의 해당게임 투표 결과를 voteType 으로 리턴합니다.
//     */
//    ResponseDto<VoteDto> getVoteTypeInBgByUserNo(VoteDto voteDto);

    /**
     *
     * @param voteDto 투표정보를 담고있는 DTO 로,
     *                null 값이아닌 userNo, bgNo, voteType 필드를 포함해야합니다.
     */
    ResponseDto<Void> updateVote(VoteDto voteDto);

//    /**
//     *
//     * @param voteDto 투표정보를 담고있는 DTO 로,
//     *                null 값이아닌 userNo, bgNo 필드를 포함해야합니다.
//     */
//    ResponseDto<Void> deleteVote(VoteDto voteDto);
}
