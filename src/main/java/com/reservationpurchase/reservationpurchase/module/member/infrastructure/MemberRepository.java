package com.reservationpurchase.reservationpurchase.module.infrastructure;

import com.reservationpurchase.reservationpurchase.module.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
