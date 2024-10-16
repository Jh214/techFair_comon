package techfair_comon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bg")
@Getter
public class Bg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bgNo;
    @Column(name = "bg_dec")
    private int bgDec;
    @Column(name = "bg_red", nullable = false)
    private String bgRed;
    @Column(name = "bg_blue", nullable = false)
    private String bgBlue;
    @Column(name = "bg_title", nullable = false)
    private String bgTitle;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
