package com.reservationpurchase.reservationpurchase.module.member.domain.entity;

import com.reservationpurchase.reservationpurchase.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "member_password")
    private String memberPassword;

    @Column(name = "member_profile_image")
    private String memberProfileImage;

    @Column(name = "member_greetings")
    private String memberGreetings;

    //following 컬럼 추가해야함

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.memberPassword = passwordEncoder.encode(this.memberPassword);
    }
}
