package com.reservationpurchase.reservationpurchase.module.newsfeed.infrastructure;

import com.reservationpurchase.reservationpurchase.module.newsfeed.domain.entity.Newsfeed;
import io.swagger.models.auth.In;
import org.hibernate.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {

//    @Query("select n from Newsfeed n where content_provider = :inputId or user_id IN (select following_id from Follow where follower_id = :inputId)")
//    List<Newsfeed> findByUserIdOrContentProvider(@Param("inputId") Long inputId);

//    @Query("SELECT n.id,n.message FROM Newsfeed n WHERE n.contentProvider = :userId OR n.userId IN (select f.following from Follow f where f.follower = :userId)")
//    List<Newsfeed> findByUserIdOrContentProvider(@Param("userId") Long userId);

//    @Query("SELECT n.id,n.message FROM Newsfeed n WHERE n.contentProvider = :userId OR n.userId = :followList")
//    List<Newsfeed> findByUserIdOrContentProvider(@Param("userId") Long userId, @Param("followList")List<Long> followList);
    @Query("SELECT n FROM Newsfeed n WHERE n.contentProvider = :userId OR n.userId IN :followList")
    List<Newsfeed> findByUserIdOrContentProvider(@Param("userId") Long userId, @Param("followList") List<Long> followList);


}


