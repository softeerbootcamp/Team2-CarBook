package softeer.carbook.domain.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import softeer.carbook.domain.user.model.User;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private final User user = new User("test@gmail.com", "nickname",
            BCrypt.hashpw("password", BCrypt.gensalt()));

    @Test
    void addUser() {
        userRepository.addUser(user);
        User saveUser = userRepository.findUserByEmail(user.getEmail());
        assertThat(saveUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void modifyNickname() {
    }

    @Test
    void modifyPassword() {
    }

    @Test
    void isEmailDuplicated() {
    }

    @Test
    void isNicknameDuplicated() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void findUserByNickname() {
    }

    @Test
    void getFollowingNicknames() {
    }

    @Test
    void getFollowerNicknames() {
    }
}