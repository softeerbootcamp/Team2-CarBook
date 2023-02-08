package softeer.carbook.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupForm {
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private final String email;

    @Size(max = 16, message = "비밀번호는 16자 이하여야 합니다.")
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private final String password;

    @Size(max = 16, message = "닉네임은 16자 이하여야 합니다.")
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private final String nickname;

    public SignupForm(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
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
