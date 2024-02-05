package com.reservationpurchase.reservationpurchase.module.user.domain.entity;

import com.reservationpurchase.reservationpurchase.common.base.BaseEntity;
import com.reservationpurchase.reservationpurchase.module.follow.domain.entity.Follow;
import com.reservationpurchase.reservationpurchase.module.like.domain.entity.Like;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    //팔로우
    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follow> followings;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follow> followers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_profile_image")
    private String userProfileImage;

    @Column(name = "user_greetings")
    private String userGreetings;

    public void update(String userName, String userProfileImage, String userGreetings) {
        this.userName = userName;
        this.userProfileImage = userProfileImage;
        this.userGreetings = userGreetings;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(this.userPassword);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
