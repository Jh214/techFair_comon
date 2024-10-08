package techfair_comon.entity.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import techfair_comon.entity.Bg;
import techfair_comon.entity.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;
    @Column(name = "content", nullable = false)
    private String content;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "is_anoymous", nullable = false)
    private IsAnoymous isAnoymous;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bgNo")
    private Bg bg;
}
