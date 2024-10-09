package techfair_comon.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import techfair_comon.entity.Bg;

import java.util.List;

@Repository
public interface BgRepository extends JpaRepository<Bg, Long> {

    @Query("SELECT bg.user.userNo FROM Bg bg WHERE bg.bgNo = ?1")
    Long findUserNoByBgNo(Long bgNo);
}
