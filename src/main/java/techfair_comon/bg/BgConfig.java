package techfair_comon.bg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BgConfig {

    @Bean
    public BgService bgService(BgRepository bgRepository) {
        return new BgServiceImpl(bgRepository);
    }
}
