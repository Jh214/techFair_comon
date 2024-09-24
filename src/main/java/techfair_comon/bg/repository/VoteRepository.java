package techfair_comon.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techfair_comon.entity.vote.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
