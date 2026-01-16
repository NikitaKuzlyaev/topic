package com.topic.service.impl;

import com.topic.entity.main.User;
import com.topic.repository.UserRepository;
import com.topic.service.UserService;
import com.topic.service.dto.UserCreateDto;
import com.topic.service.dto.UserDto;
import com.topic.util.annotations.Logging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserCreateDto data) {

        User user = new User();
        user.setUsername(data.username());
        user.setLogin(data.login());
        user.setHashedPasswordAndSalt(data.hashedPasswordAndSalt());

        var res = userRepository.save(user);
        return UserServiceImplUtil.mapToUserDto(res);
    }

    @Override
    public Optional<UserDto> getUserById(Long userId) {
        Optional<User> data = userRepository.findById(userId);
        return packToOptionalResponse(data);
    }

    @Override
    @Logging
    public Optional<UserDto> getUserByLogin(String login) {
        Optional<User> data = userRepository.findByLogin(login);
        return packToOptionalResponse(data);
    }

    private Optional<UserDto> packToOptionalResponse(Optional<User> data){
        if (data.isEmpty()) {
            return Optional.empty();
        }
        User user = data.get();
        return Optional.of(new UserDto(
                user.getId(), user.getUsername(), user.getLogin(), user.getHashedPasswordAndSalt())
        );
    }
}

// todo: перенести
class UserServiceImplUtil{

    static UserDto mapToUserDto(User data){
        return new UserDto(
                data.getId(), data.getUsername(), data.getLogin(), data.getHashedPasswordAndSalt()
        );
    }

}