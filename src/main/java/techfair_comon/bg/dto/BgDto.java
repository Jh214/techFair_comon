package techfair_comon.bg.dto;

import lombok.Builder;
import lombok.Getter;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class BgDto {
    private Long bgNo;
    private int bgDec;
    private String bgRed;
    private String bgBlue;
    private String bgTitle;
    private LocalDateTime createdAt;
    private User user;

    public Bg toEntity() {
        return Bg.builder()
                .bgNo(this.bgNo)
                .bgDec(this.bgDec)
                .bgRed(this.bgRed)
                .bgBlue(this.bgBlue)
                .bgTitle(this.bgTitle)
                .createdAt(this.createdAt)
                .user(this.user)
                .build();
    }
    public static BgDto fromEntity(Bg bg) {
        return BgDto.builder()
                .bgNo(bg.getBgNo())
                .bgDec(bg.getBgDec())
                .bgRed(bg.getBgRed())
                .bgBlue(bg.getBgBlue())
                .bgTitle(bg.getBgTitle())
                .createdAt(bg.getCreatedAt())
                .user(bg.getUser())
                .build();
    }
}

