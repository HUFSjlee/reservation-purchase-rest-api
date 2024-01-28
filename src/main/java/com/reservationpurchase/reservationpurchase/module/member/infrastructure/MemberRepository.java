package com.reservationpurchase.reservationpurchase.module.member.infrastructure;

import com.reservationpurchase.reservationpurchase.module.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberEmail(String memberEmail);
}
