package techfair_comon.entity.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;

@Entity
@Table(name = "report")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Report {

    @EmbeddedId
    private ReportId id;

    @MapsId("userNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private User user;

    @MapsId("bgNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bg_no", insertable = false, updatable = false)
    private Bg bg;

}
