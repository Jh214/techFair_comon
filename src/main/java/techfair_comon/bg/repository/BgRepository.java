package techfair_comon.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techfair_comon.entity.Bg;

@Repository
public interface BgRepository extends JpaRepository<Bg, Long> {

}
