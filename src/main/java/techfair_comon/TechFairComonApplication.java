package techfair_comon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TechFairComonApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechFairComonApplication.class, args);
    }

}
