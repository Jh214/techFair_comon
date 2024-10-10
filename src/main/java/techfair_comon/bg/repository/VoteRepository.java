package techfair_comon.bg.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techfair_comon.bg.dto.VoteResultDto;
import techfair_comon.entity.vote.Vote;
import techfair_comon.entity.vote.VoteId;
import techfair_comon.entity.vote.VoteType;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {
    @Query(value = "SELECT bg.bg_no, vote.vote_type, COUNT(*) FROM vote JOIN bg ON vote.bg_no = bg.bg_no WHERE vote.bg_no IN (SELECT bg_no FROM vote WHERE user_no = :#{#paramVote.user.userNo}) GROUP BY bg.bg_no, vote.vote_type", nativeQuery = true)
    List<Object[]> countVotesByVoteTypeGroupedByUser(@Param("paramVote") Vote vote);


    @Query(value = "SELECT bg.bg_no, vote.vote_type, COUNT(*) FROM vote JOIN bg ON vote.bg_no = bg.bg_no WHERE vote.bg_no = :#{#paramVote.bg.bgNo} GROUP BY bg.bg_no, vote.vote_type", nativeQuery = true)
    List<Object[]> countVotesByVoteTypeGroupedByBg(@Param("paramVote") Vote vote);


    @Query(value = "SELECT bg.bg_no, vote.vote_type, COUNT(*) FROM vote JOIN bg ON vote.bg_no = bg.bg_no GROUP BY bg.bg_no, vote.vote_type", nativeQuery = true)
    List<Object[]> countAllVotesByVoteTypeGrouped();

    @Query("SELECT v.voteType FROM Vote v WHERE v.user.userNo = :#{#paramVote.user.userNo} AND v.bg.bgNo = :#{#paramVote.bg.bgNo}")
    Optional<VoteType> findVotedByUserAndBg(@Param("paramVote") Vote vote);

    @Transactional
    @Modifying
    @Query("UPDATE Vote v SET v.voteType = :#{#paramVote.voteType} WHERE v.user.userNo = :#{#paramVote.user.userNo} AND v.bg.bgNo = :#{#paramVote.bg.bgNo}")
    int updateVoteTypeByUserAndBg(@Param("paramVote") Vote vote);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.user.userNo = :#{#paramVote.user.userNo} AND v.bg.bgNo = :#{#paramVote.bg.bgNo}")
    int deleteVoteByUserAndBg(@Param("paramVote") Vote vote);

    @Query("SELECT COUNT(*) FROM Vote v WHERE v.user.userNo = :#{#paramVote.user.userNo} AND v.bg.bgNo = :#{#paramVote.bg.bgNo}")
    int authByUserNo(@Param("paramVote") Vote vote);

}
