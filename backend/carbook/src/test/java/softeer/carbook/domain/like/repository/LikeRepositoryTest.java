package softeer.carbook.domain.like.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import softeer.carbook.domain.follow.repository.FollowRepository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql("classpath:create_table.sql")
@Sql("classpath:create_data.sql")
class LikeRepositoryTest {
    private LikeRepository likeRepository;
    @Autowired private DataSource dataSource;

    @BeforeEach
    void setUp(){
        likeRepository = new LikeRepository(dataSource);
    }

    @Test
    @DisplayName("Post Id를 통한 좋아요 수 조회 테스트")
    void findLikeCountByPostId() {
        // when
        int likeCount = likeRepository.findLikeCountByPostId(1);

        // then
        assertThat(likeCount).isEqualTo(3);
    }

    @Test
    @DisplayName("좋아요 체크 테스트")
    void checkLike() {
        // when
        boolean isLikeTrue = likeRepository.checkLike(1, 1);
        boolean isLikeFalse1 = likeRepository.checkLike(3, 4); // isDelete = 1
        boolean isLikeFalse2 = likeRepository.checkLike(1, 4);

        // then
        assertThat(isLikeTrue).isTrue();
        assertThat(isLikeFalse1).isFalse();
        assertThat(isLikeFalse2).isFalse();
    }

    @Test
    @DisplayName("UserId와 PostId로 LikeId 받아오기 테스트")
    void findLikeByUserIdAndPostId() {
        // when
        int likeId = likeRepository.findLikeByUserIdAndPostId(1, 1).get();
        int likeId2 = likeRepository.findLikeByUserIdAndPostId(4, 3).get();

        // then
        assertThat(likeId).isEqualTo(1);
        assertThat(likeId2).isEqualTo(7);
    }

    @Test
    @DisplayName("좋아요 취소 테스트")
    void unLike() {
        // when
        likeRepository.unLike(1);
        boolean isUnLike = likeRepository.findLikeByUserIdAndPostId(1, 1).isEmpty();

        // then
        assertThat(isUnLike).isTrue();
    }

    @Test
    @DisplayName("좋아요 추가 테스트")
    void addLike() {
        // when
        likeRepository.addLike(1, 4);
        boolean existLike = likeRepository.findLikeByUserIdAndPostId(1, 4).isPresent();
        likeRepository.addLike(3, 4); // isDeleted = 1
        boolean existLikeWhenIsDeleted = likeRepository.findLikeByUserIdAndPostId(3, 4).isPresent();

        // then
        assertThat(existLike).isTrue();
        assertThat(existLikeWhenIsDeleted).isTrue();
    }
}