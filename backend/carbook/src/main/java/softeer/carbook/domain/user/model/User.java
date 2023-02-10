package softeer.carbook.domain.user.model;

import softeer.carbook.domain.user.dto.SignupForm;

public class User {
    private final int id;
    private final String email;
    private final String password;
    private final String nickname;


    public User(int id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String email, String nickname, String password) {
        this.id = 0;
        this.email = email;
        this.nickname = nickname;
        this.password=  password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

}
