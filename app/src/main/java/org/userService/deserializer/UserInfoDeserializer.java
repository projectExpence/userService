package org.userService.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.LoggerFactory;
import org.userService.dto.UserInfoDto;
import org.slf4j.Logger;


public class UserInfoDeserializer implements Deserializer<UserInfoDto> {

    private static final Logger log= LoggerFactory.getLogger(UserInfoDeserializer.class);

    private final ObjectMapper objectMapper;


    public UserInfoDeserializer() {
        this.objectMapper = new ObjectMapper();
    }


    public UserInfoDeserializer(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }

    @Override
    public UserInfoDto deserialize(String args0, byte[] args1) {
        if(args1==null){
            return null;
        }

        UserInfoDto user = null;
        try{
            user = objectMapper.readValue(args1,UserInfoDto.class);
        } catch (Exception e){
            log.error("Failed to deserialize UserInfoDto",e);
        }
        return user;
    }
}
