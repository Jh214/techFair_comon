package techfair_comon.bg.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;

@Getter @Setter
public class CreateBgDto {
    @NotBlank(message = "bgRed cannot be null")
    private String bgRed;
    @NotBlank(message = "bgBlue cannot be null")
    private String bgBlue;
    @NotBlank(message = "bgTitle cannot be null")
    private String bgTitle;
    private User user;

    public Bg toEntity() {
        return Bg.builder()
                .bgRed(this.bgRed)
                .bgBlue(this.bgBlue)
                .bgTitle(this.bgTitle)
                .user(this.user)
                .build();
    }
}
