package com.reservationpurchase.reservationpurchase.module.newsfeed.domain.service;

import com.reservationpurchase.reservationpurchase.module.newsfeed.domain.entity.Newsfeed;
import com.reservationpurchase.reservationpurchase.module.newsfeed.infrastructure.NewsfeedRepository;
import com.reservationpurchase.reservationpurchase.module.newsfeed.presentation.dto.NewsfeedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;

    public List<String> read(NewsfeedDTO.CreateRequest request) {
        List<Long> followList = new ArrayList<>();
        followList.add(3L);
        List<Newsfeed> all = newsfeedRepository.findByUserIdOrContentProvider(request.getUserId(),followList);

        List<String> newsFeedMsg = all.stream().map(Newsfeed::getMessage).collect(Collectors.toList());

//        NewsfeedDTO.CreateResponse response = NewsfeedDTO.CreateResponse.builder()
//                .userIds(userIds)
//                .build();

        return newsFeedMsg;
    }

    public NewsfeedDTO.CreateResponse createnewsfeed(Long createUser, Long providerUser, String message) {

        Newsfeed newsfeed = Newsfeed.builder()
                .userId(createUser)
                .contentProvider(providerUser)
                .message(message)
                .build();

        Newsfeed savedEntity = newsfeedRepository.save(newsfeed);

        NewsfeedDTO.CreateResponse response = NewsfeedDTO.CreateResponse.builder()
                .userId(savedEntity.getUserId())
                .contentProvider(savedEntity.getContentProvider())
                .message(savedEntity.getMessage())
                .build();

        return response;
    }
}
