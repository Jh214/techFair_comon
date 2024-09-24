package techfair_comon.bg.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import techfair_comon.entity.Bg;

@Getter @Setter
public class CreateBgDto {
    @NotBlank(message = "bgRed cannot be null")
    private String bgRed;
    @NotBlank(message = "bgBlue cannot be null")
    private String bgBlue;
    @NotBlank(message = "bgTitle cannot be null")
    private String bgTitle;

    public Bg toEntity() {
        Bg bg = new Bg();
        bg.setBgRed(this.bgRed);
        bg.setBgBlue(this.bgBlue);
        bg.setBgTitle(this.bgTitle);
        return bg;
    }
}
