package softeer.carbook.domain.follow.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;
import softeer.carbook.global.dto.Message;

import javax.sql.DataSource;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql("classpath:create_table.sql")
@Sql("classpath:create_data.sql")
class FollowRepositoryTest {
    private FollowRepository followRepository;
    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp(){
        followRepository = new FollowRepository(dataSource);
    }

    @Test
    @DisplayName("팔로워 id 조회 테스트")
    void getFollowerIds() {
        // when
        List<Integer> followerIds = followRepository.getFollowerIds(3);

        // then
        assertThat(followerIds.get(0)).isEqualTo(1);
        assertThat(followerIds.get(1)).isEqualTo(4);
    }

    @Test
    @DisplayName("팔로잉 id 조회 테스트")
    void getFollowingIds() {
        // when
        List<Integer> followingIds = followRepository.getFollowingIds(1);

        // then
        assertThat(followingIds.get(0)).isEqualTo(2);
        assertThat(followingIds.get(1)).isEqualTo(3);
    }

    @Test
    @DisplayName("팔로워수 조회 테스트")
    void getFollowerCount() {
        // when
        int followerCount = followRepository.getFollowerCount(3);

        // then
        assertThat(followerCount).isEqualTo(2);
    }

    @Test
    @DisplayName("팔로잉수 조회 테스트")
    void getFollowingCount() {
        // when
        int followingCount = followRepository.getFollowingCount(1);

        // then
        assertThat(followingCount).isEqualTo(2);
    }

    @Test
    @DisplayName("팔로우 체크 테스트")
    void isFollow() {
        // when
        boolean isFollowTrue = followRepository.isFollow(1, 2);
        boolean isFollowFalse = followRepository.isFollow(1, 4);

        // then
        assertThat(isFollowTrue).isTrue();
        assertThat(isFollowFalse).isFalse();
    }

    @Test
    @DisplayName("팔로워 id와 팔로잉 id로 pk id 조회 테스트")
    void findFollowId() {
        // when
        int id1 = followRepository.findFollowId(1 ,2).get();
        int id3 = followRepository.findFollowId(2, 1).get();

        // then
        assertThat(id1).isEqualTo(1);
        assertThat(id3).isEqualTo(4);
    }

    @Test
    @DisplayName("언팔로우 테스트")
    void unFollow() {
        // when
        followRepository.unFollow(1);
        boolean isFollow = followRepository.isFollow(1, 2);

        // then
        assertThat(isFollow).isFalse();
    }

    @Test
    @DisplayName("팔로우 추가 테스트")
    void addFollow() {
        // when
        followRepository.addFollow(3, 4);
        int id = followRepository.findFollowId(3, 4).get();

        // then
        assertThat(id).isEqualTo(7);
    }
}