package org.userService.consumer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.userService.dto.UserInfoDto;
import org.userService.entities.UserInfo;
import org.userService.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class AuthServiceConsumer {

    private final UserRepository userRepository;
    private final Counter kafkaUserProcessed;


    @Autowired
    public AuthServiceConsumer(UserRepository userRepository){
        this.userRepository=userRepository;
        this.kafkaUserProcessed = Counter.builder("kafka_user_processed_total")
                .description("Number of Kafka user messages processed")
                .register(Metrics.globalRegistry);
    }


    @KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto userInfoDto){
        try {
            Optional<UserInfo> existingUserOpt = userRepository.findByEmail(userInfoDto.getEmail());

            if (existingUserOpt.isPresent()) {
                UserInfo existingUser = existingUserOpt.get();

                // Only update mutable fields (not email/username/userId)
                existingUser.setFirstName(userInfoDto.getFirstName());
                existingUser.setLastName(userInfoDto.getLastName());
                existingUser.setProfilePictureUrl(userInfoDto.getProfilePictureUrl());

                userRepository.save(existingUser);
                log.info("🔄 User updated: {}", userInfoDto.getEmail());
            } else {
                UserInfo newUser = UserInfo.builder()
                        .userId(userInfoDto.getUserId())
                        .firstName(userInfoDto.getFirstName())
                        .lastName(userInfoDto.getLastName())
                        .username(userInfoDto.getUsername())
                        .email(userInfoDto.getEmail())
                        .profilePictureUrl(userInfoDto.getProfilePictureUrl())
                        .build();

                userRepository.save(newUser);
                log.info("✅ New user saved: {}", userInfoDto.getEmail());
            }

            kafkaUserProcessed.increment();

        } catch (Exception e){
            log.error("❌ Error processing Kafka message for user {}: {}", userInfoDto.getEmail(), e.getMessage(), e);
        }
    }
}
