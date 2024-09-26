package techfair_comon.bg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import techfair_comon.bg.repository.BgRepository;
import techfair_comon.bg.repository.VoteRepository;
import techfair_comon.bg.service.BgService;
import techfair_comon.bg.service.BgServiceImpl;
import techfair_comon.bg.service.VoteService;
import techfair_comon.bg.service.VoteServiceImpl;

@Configuration
public class DIConfig {

    @Bean
    public BgService bgService(BgRepository bgRepository) {
        return new BgServiceImpl(bgRepository);
    }

    @Bean
    public VoteService voteService(VoteRepository voteRepository) {
        return new VoteServiceImpl(voteRepository);
    }
}
