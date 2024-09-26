package techfair_comon.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techfair_comon.entity.vote.Vote;
import techfair_comon.entity.vote.VoteId;
import techfair_comon.entity.vote.VoteType;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {

    @Query("SELECT v.bg, v.voted, COUNT(v) FROM Vote v WHERE v.user.userNo =:#{#paramVote.id.userNo} GROUP BY v.voted")
    List<Object[]> countVotesByVoteTypeGroupedByUser(@Param("paramVote") Vote vote);

    @Query("SELECT v.bg, v.voted, COUNT(v) FROM Vote v WHERE v.bg.bgNo = :#{#paramVote.id.bgNo} GROUP BY v.voted")
    List<Object[]> countVotesByVoteTypeGroupedByBg(@Param("paramVote") Vote vote);

    @Query("SELECT v.bg, v.voted, COUNT(v) FROM Vote v GROUP BY v.voted")
    List<Object[]> countAllVotesByVoteTypeGrouped();

    @Query("SELECT v.voted FROM Vote v WHERE v.user.userNo = :#{#paramVote.id.userNo} AND v.bg.bgNo = :#{#paramVote.id.bgNo}")
    Optional<VoteType> findVotedByUserAndBg(@Param("paramVote") Vote vote);

    @Modifying
    @Query("UPDATE Vote v SET v.voted = :#{#paramVote.voted} WHERE v.user.userNo = :#{paramVote.id.userNo} AND v.bg.bgNo = :#{#paramVote.id.bgNo}")
    void updateVoteTypeByUserAndBg(@Param("paramVote") Vote vote);

    @Modifying
    @Query("DELETE FROM Vote v WHERE v.user.userNo = :#{#paramVote.id.userNo} AND v.bg.bgNo = :#{#paramVote.id.bgNo}")
    void deleteVoteByUserAndBg(@Param("paramVote") Vote vote);



}
