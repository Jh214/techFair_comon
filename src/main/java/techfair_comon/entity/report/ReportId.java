package techfair_comon.entity.report;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReportId implements Serializable {
    @Column(name = "user_no")
    private Long userNo;
    @Column(name = "bg_no")
    private Long bgNo;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof ReportId)) return false;
        ReportId reportId = (ReportId)obj;
        return userNo.equals(reportId.userNo) && bgNo.equals(reportId.bgNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo, bgNo);
    }
}
