package softeer.carbook.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ModifyNickNameForm {
    @Size(max = 16, message = "닉네임은 16자 이하여야 합니다.")
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private final String newNickname;

    // json 파싱 때문에 빈 생성자를 생성해 주어야 함..
    public ModifyNickNameForm() {
        newNickname = null;
    }

    public ModifyNickNameForm(String newNickname) {
        this.newNickname = newNickname;
    }

    public String getNewNickname() {
        return newNickname;
    }
}
