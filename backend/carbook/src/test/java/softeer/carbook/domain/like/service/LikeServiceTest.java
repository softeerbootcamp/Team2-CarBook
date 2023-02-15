package softeer.carbook.domain.like.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import softeer.carbook.domain.like.repository.LikeRepository;
import softeer.carbook.domain.user.repository.UserRepository;
import softeer.carbook.global.dto.Message;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {
    @InjectMocks
    private LikeService likeService;
    @Mock
    private LikeRepository likeRepository;

    @Test
    @DisplayName("좋아요 서비스 테스트")
    void like() {
        // given
        given(likeRepository.findLikeByUserIdAndPostId(anyInt(), anyInt()))
                .willReturn(Optional.empty());

        // when
        Message result = likeService.modifyLikeInfo(anyInt(), anyInt());

        // then
        assertThat(result.getMessage()).isEqualTo("Like Success");
        verify(likeRepository).findLikeByUserIdAndPostId(anyInt(), anyInt());
        verify(likeRepository).addLike(anyInt(), anyInt());
    }

    @Test
    @DisplayName("좋아요 취소 서비스 테스트")
    void unLike() {
        // given
        given(likeRepository.findLikeByUserIdAndPostId(anyInt(), anyInt()))
                .willReturn(Optional.of(1));

        // when
        Message result = likeService.modifyLikeInfo(anyInt(), anyInt());

        // then
        assertThat(result.getMessage()).isEqualTo("UnLike Success");
        verify(likeRepository).findLikeByUserIdAndPostId(anyInt(), anyInt());
        verify(likeRepository).unLike(anyInt());
    }

}
