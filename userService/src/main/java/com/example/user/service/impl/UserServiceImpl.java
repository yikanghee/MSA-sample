package com.example.user.service.impl;

import com.example.user.dto.UserDto;
import com.example.user.jpa.entity.UserEntity;
import com.example.user.jpa.reposiotry.UserRepository;
import com.example.user.service.UserService;
import com.example.user.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity user = mapper.map(userDto, UserEntity.class);
        user.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(user);

        return null;
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseOrder> orderList = new ArrayList<>();
        userDto.setResponseOrders(orderList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    public boolean isDuplicatedUserId(long userId) throws Exception {

        if (userRepository.findById(userId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 아이디입니다.");
        }

        return true;
    }


}
