package techfair_comon.bg.dto;

import lombok.Builder;
import lombok.Getter;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;
import techfair_comon.entity.report.Report;
import techfair_comon.entity.report.ReportId;

@Builder
@Getter
public class ReportDto {
    private User user;
    private Bg bg;

    public Report toEntity() {
        return Report.builder()
                .id(ReportId.builder()
                        .build())
                .user(this.user)
                .bg(this.bg)
                .build();
    }

    public static ReportDto fromEntity(Report report) {
        return ReportDto.builder()
                .user(report.getUser())
                .bg(report.getBg())
                .build();
    }
}
