package techfair_comon.bg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import techfair_comon.bg.repository.BgRepository;
import techfair_comon.bg.service.BgService;
import techfair_comon.bg.service.BgServiceImpl;

@Configuration
public class BgConfig {

    @Bean
    public BgService bgService(BgRepository bgRepository) {
        return new BgServiceImpl(bgRepository);
    }
}
