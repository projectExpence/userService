package org.userService.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.userService.dto.UserInfoDto;
import org.userService.entities.UserEntity;
import org.userService.repository.UserRepository;

@Service
@Slf4j
public class AuthServiceConsumer {

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceConsumer(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto userInfoDto){
        try{
            UserEntity userEntity = UserEntity.builder()
                    .userId(userInfoDto.getUserId())
                    .firstName(userInfoDto.getFirstName())
                    .lastName(userInfoDto.getLastName())
                    .username(userInfoDto.getUsername())
                    .email(userInfoDto.getEmail())
                    .profilePictureUrl(userInfoDto.getProfilePictureUrl())
                    .build();
            userRepository.save(userEntity);
            log.info("✅ User saved/updated: {}",userInfoDto.getEmail());
        }catch (Exception e){
            log.error("❌ Error processing Kafka message: {}", e.getMessage(), e);
        }
    }
}
