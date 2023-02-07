package softeer.carbook.domain.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.exception.LoginEmailNotExistException;
import softeer.carbook.domain.user.exception.NicknameNotExistException;
import softeer.carbook.domain.user.exception.idNotExistException;
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

    public String findEmailByNickname(String nickname) {
        List<String> result = jdbcTemplate.query("select email from USER where nickname = ?", emailRowMapper(), nickname);
        return result.stream().findAny().orElseThrow(
                () -> new NicknameNotExistException("ERROR: Nickname not exist")
        );
    }

    public int findUserIdByNickname(String nickname) {
        List<Integer> result = jdbcTemplate.query("select id from USER where nickname = ?", idRowMapper(), nickname);
        return result.stream().findAny().orElseThrow(
                () -> new idNotExistException("ERROR: Id not exist")
        );
    }

    private RowMapper<String> emailRowMapper(){
        return (rs, rowNum) -> rs.getString("email");
    }

    private RowMapper<Integer> idRowMapper(){
        return (rs, rowNum) -> rs.getInt("id");
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
