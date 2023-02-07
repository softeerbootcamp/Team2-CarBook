package softeer.carbook.domain.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.model.User;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addUser(SignupForm signupForm) {
        jdbcTemplate.update("insert into USER(email, password, nickname) values(?, ?, ?)",
                signupForm.getEmail(),
                signupForm.getPassword(),
                signupForm.getNickname()
                );
    }

    public boolean isEmailDuplicated(String email) {
        List<User> result = jdbcTemplate.query("select * from USER where email = ?", userRowMapper(), email);
        return !result.isEmpty();
    }

    public boolean isNicknameDuplicated(String nickname) {
        List<User> result = jdbcTemplate.query("select * from USER where nickname = ?", userRowMapper(), nickname);
        return !result.isEmpty();
    }

    public User findUserByEmail(String email){
        List<User> result = jdbcTemplate.query("select * from USER where email = ?", userRowMapper(), email);
        return result.stream().findAny().orElseThrow(
                () -> new LoginEmailNotExistException("ERROR: Email not exist")
        );
    }

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> {
            User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("nickname"),
                    rs.getString("password")
            );
            return user;
        };
    }

}
