package techfair_comon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no") // 사용자 닉네임
    private Long userNo;

    @Column(name ="user_id", unique = true)
    private String userId; // 사용자 ID (로그인용)

    @Column(name ="user_pw",nullable = false)
    private String userPw; // 비밀번호

    @Column(name ="user_name",nullable = false)
    private String userName; // 사용자 이름

    @Column(name ="user_tel",nullable = false)
    private String userTel; // 사용자 전화번호

}
