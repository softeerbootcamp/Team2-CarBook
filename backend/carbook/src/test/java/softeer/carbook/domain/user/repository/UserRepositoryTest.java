package softeer.carbook.domain.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.user.model.User;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql("classpath:create_table.sql")
class UserRepositoryTest {
    private UserRepository userRepository;
    private FollowRepository followRepository;
    @Autowired private DataSource dataSource;

    @BeforeEach
    void setUp(){
        userRepository = new UserRepository(dataSource);
        followRepository = new FollowRepository(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("delete from `USER`");
        jdbcTemplate.update("delete from FOLLOW");
    }

    private final User user = new User("test@gmail.com", "nickname", "password");
    private final User user1 = new User("user1@gmail.com", "user1", "password");
    private final User user2 = new User("user2@gmail.com", "user2", "password");

    @Test
    @DisplayName("유저 추가 테스트")
    void addUser() {
        // given
        userRepository.addUser(user);

        // when
        User saveUser = userRepository.findUserByEmail(user.getEmail());

        // then
        assertThat(saveUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(saveUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(saveUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    void modifyNickname() {
        // given
        userRepository.addUser(user);

        // when
        userRepository.modifyNickname(user.getNickname(), "newnickname");

        // then
        User savedUser = userRepository.findUserByEmail(user.getEmail());
        assertThat("newnickname").isEqualTo(savedUser.getNickname());
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void modifyPassword() {
        // given
        userRepository.addUser(user);

        // when
        userRepository.modifyPassword(userRepository.findUserByEmail(user.getEmail()).getId(), "newpassword");
        User savedUser = userRepository.findUserByEmail(user.getEmail());

        // then
        assertThat("newpassword").isEqualTo(savedUser.getPassword());
    }

    @Test
    @DisplayName("이메일 중복 테스트")
    void isEmailDuplicated() {
        // given
        userRepository.addUser(user);

        // when & then
        assertThat(userRepository.isEmailDuplicated(user.getEmail())).isTrue();
        assertThat(userRepository.isEmailDuplicated("other@example.com")).isFalse();
    }

    @Test
    @DisplayName("닉네임 중복 테스트")
    void isNicknameDuplicated() {
        // given
        userRepository.addUser(user);

        // when & then
        assertThat(userRepository.isNicknameDuplicated(user.getNickname())).isTrue();
        assertThat(userRepository.isNicknameDuplicated("otherUSer")).isFalse();
    }

    @Test
    @DisplayName("id로 유저 찾기 테스트")
    void findUserById() {
        // given
        userRepository.addUser(user);

        // when
        User foundUser = userRepository.findUserById(userRepository.findUserByEmail(user.getEmail()).getId());

        // then
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("이메일로 유저 찾기 테스트")
    void findUserByEmail() {
        // given
        userRepository.addUser(user);

        // when
        User foundUser = userRepository.findUserByEmail(user.getEmail());

        // then
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("닉네임으로 유저 찾기 테스트")
    void findUserByNickname() {
        // given
        userRepository.addUser(user);

        // when
        User foundUser = userRepository.findUserByNickname(user.getNickname());

        // then
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("팔로잉 리스트 받기 테스트")
    void getFollowingNicknames() {
        // given
        userRepository.addUser(user);
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        followRepository.addFollow(
                userRepository.findUserByEmail(user.getEmail()).getId(),
                userRepository.findUserByEmail(user1.getEmail()).getId());
        followRepository.addFollow(
                userRepository.findUserByEmail(user.getEmail()).getId(),
                userRepository.findUserByEmail(user2.getEmail()).getId());

        // when
        List<String> nicknames = userRepository.getFollowingNicknames(user.getNickname());

        // then
        assertThat(nicknames.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("팔로워 리스트 받기 테스트")
    void getFollowerNicknames() {
        // given
        userRepository.addUser(user);
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        followRepository.addFollow(
                userRepository.findUserByEmail(user1.getEmail()).getId(),
                userRepository.findUserByEmail(user.getEmail()).getId());
        followRepository.addFollow(
                userRepository.findUserByEmail(user2.getEmail()).getId(),
                userRepository.findUserByEmail(user.getEmail()).getId());

        // when
        List<String> nicknames = userRepository.getFollowerNicknames(user.getNickname());

        // then
        assertThat(nicknames.size()).isEqualTo(2);
    }
}