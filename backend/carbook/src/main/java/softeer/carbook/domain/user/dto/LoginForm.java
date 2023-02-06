package softeer.carbook.domain.user.dto;

public class LoginForm {
    private final String email;
    private final String password;

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
