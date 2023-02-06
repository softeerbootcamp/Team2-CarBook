package softeer.carbook.domain.user.dto;

public class SignupForm {
    private final String email;
    private final String nickname;
    private final String password;

    public SignupForm(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }


}
