package com.example.user;

import com.example.user.jpa.entity.UserEntity;
import com.example.user.jpa.reposiotry.UserRepository;
import com.example.user.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class})
class UserServiceApplicationTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl target;

    @Test
    @DisplayName("회원 생성")
    void test() {
        doReturn(Optional.of(user())).when(userRepository).findById(any());

        ResponseStatusException result = assertThrows(ResponseStatusException.class, () ->
                target.isDuplicatedUserId(1l));
    }

    @Test
    @DisplayName("사용자 아이디 사용가능")
    void test1() throws Exception {
        doReturn(Optional.empty()).when(userRepository).findById(any());

        final boolean result = target.isDuplicatedUserId(1l);

        Assertions.assertThat(result).isEqualTo(true);

        verify(userRepository, times(1)).findById(any());
    }

    public UserEntity user() {
        return UserEntity.builder()
                .id(1l)
                .userUUID(UUID.randomUUID().toString())
                .build();
    }

}
