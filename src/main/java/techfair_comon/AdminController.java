package techfair_comon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class AdminController {
    @GetMapping
    public String admin() {
        return "테크페어 페이지입니다.";
    }

}
