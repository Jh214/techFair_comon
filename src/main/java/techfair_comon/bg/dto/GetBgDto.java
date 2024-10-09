package techfair_comon.bg.dto;

import lombok.Getter;
import lombok.Setter;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;

import java.time.LocalDateTime;

@Getter @Setter
public class GetBgDto {
    private Long bgNo;
    private Long userNo;
    private String bgRed;
    private String bgBlue;
    private String bgTitle;
    private LocalDateTime createdAt;

    public static GetBgDto fromEntity(Bg bg) {
        GetBgDto dto = new GetBgDto();
        dto.setBgNo(bg.getBgNo());
        dto.setBgRed(bg.getBgRed());
        dto.setBgBlue(bg.getBgBlue());
        dto.setBgTitle(bg.getBgTitle());
        dto.setCreatedAt(bg.getCreatedAt());
        dto.setUserNo(bg.getUser() != null ? bg.getUser().getUserNo() : null);
        return dto;
    }

    public Bg toEntity() {
        return Bg.builder()
                .bgNo(this.bgNo)
                .bgRed(this.bgRed)
                .bgBlue(this.bgBlue)
                .bgTitle(this.bgTitle)
                .createdAt(this.createdAt)
                .user(User.builder()
                        .userNo(this.userNo)
                        .build())
                .build();
    }

}
