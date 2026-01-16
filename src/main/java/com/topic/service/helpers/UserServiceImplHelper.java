package com.topic.service.helpers;

import com.topic.entity.main.User;
import com.topic.service.dto.UserDto;

public class UserServiceImplHelper {
    public static UserDto mapToUserDto(User data){
        return new UserDto(
                data.getId(), data.getUsername(), data.getLogin(), data.getHashedPasswordAndSalt()
        );
    }
}
