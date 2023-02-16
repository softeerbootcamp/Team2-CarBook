package softeer.carbook.domain.follow.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import softeer.carbook.domain.follow.dto.FollowListResponse;
import softeer.carbook.domain.follow.exception.FollowIdNotExistException;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.post.exception.InvalidPostAccessException;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;
import softeer.carbook.global.dto.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    @InjectMocks
    private FollowService followService;

    @Mock private FollowRepository followRepository;
    @Mock private UserRepository userRepository;

    private final User user = new User(15, "user15@exam.com", "15번유저", "pw15");
    private final List<String> nicknames = new ArrayList<>(){{
        add("nickname1");
        add("nickname2");
    }};

    @Test
    @DisplayName("팔로우 기능 테스트")
    void follow() {
        // given
        given(userRepository.findUserByNickname(anyString())).willReturn(user);
        given(followRepository.findFollowId(anyInt(), anyInt())).willReturn(Optional.empty());

        // when
        Message result = followService.modifyFollowInfo(user, "nickname");

        // then
        assertThat(result.getMessage()).isEqualTo("Follow Success");
        verify(userRepository).findUserByNickname(anyString());
        verify(followRepository).findFollowId(anyInt(), anyInt());
        verify(followRepository).addFollow(anyInt(), anyInt());
    }

    @Test
    @DisplayName("언팔로우 기능 테스트")
    void unFollow() {
        // given
        given(userRepository.findUserByNickname(anyString())).willReturn(user);
        given(followRepository.findFollowId(anyInt(), anyInt())).willReturn(Optional.of(1));

        // when
        Message result = followService.modifyFollowInfo(user, "nickname");

        // then
        assertThat(result.getMessage()).isEqualTo("Unfollow Success");
        verify(userRepository).findUserByNickname(anyString());
        verify(followRepository).findFollowId(anyInt(), anyInt());
        verify(followRepository).unFollow(anyInt());
    }

    @Test
    @DisplayName("팔로잉 리스트 테스트")
    void getFollowings() {
        // given
        given(userRepository.getFollowingNicknames(anyString())).willReturn(nicknames);

        // when
        FollowListResponse followListResponse = followService.getFollowings("nickname");

        // then
        assertThat(followListResponse.getNicknames()).isEqualTo(nicknames);
        verify(userRepository).getFollowingNicknames(anyString());
    }

    @Test
    @DisplayName("팔로워 리스트 테스트")
    void getFollowers() {
        // given
        given(userRepository.getFollowerNicknames(anyString())).willReturn(nicknames);

        // when
        FollowListResponse followListResponse = followService.getFollowers("nickname");

        // then
        assertThat(followListResponse.getNicknames()).isEqualTo(nicknames);
        verify(userRepository).getFollowerNicknames(anyString());
    }

    @Test
    @DisplayName("팔로워 끊기(친삭) 테스트 - 성공")
    void deleteFollowerSuccess() {
        // given
        given(userRepository.findUserByNickname(anyString())).willReturn(user);
        given(followRepository.findFollowId(anyInt(), anyInt())).willReturn(Optional.of(1));

        // when
        Message result = followService.deleteFollower(user, "nickname");

        // then
        assertThat(result.getMessage()).isEqualTo("Follower delete success");
        verify(userRepository).findUserByNickname(anyString());
        verify(followRepository).findFollowId(anyInt(),anyInt());
        verify(followRepository).unFollow(anyInt());
    }

    @Test
    @DisplayName("팔로워 끊기(친삭) 테스트 - 실패")
    void deleteFollowerFail() {
        // given
        given(userRepository.findUserByNickname(anyString())).willReturn(user);
        given(followRepository.findFollowId(anyInt(), anyInt())).willReturn(Optional.empty());

        // when
        Throwable exception = assertThrows(FollowIdNotExistException.class, () -> {
            Message result = followService.deleteFollower(user, "nickname");
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("ERROR: FollowId not exist");
        verify(userRepository).findUserByNickname(anyString());
        verify(followRepository).findFollowId(anyInt(),anyInt());
    }
}

