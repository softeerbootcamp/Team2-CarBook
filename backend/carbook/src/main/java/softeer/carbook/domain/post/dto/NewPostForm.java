package softeer.carbook.domain.post.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class NewPostForm {
    @NotBlank(message = "사진을 등록해 주세요.")
    private final MultipartFile image;

    @Size(max = 3)
    private final List<String> hashtag;

    @NotBlank(message = "차 타입을 입력해 주세요.")
    private final String type;

    @NotBlank(message = "차 모델을 입력해 주세요.")
    private final String model;

    private final String content;

    public String getContent() {
        return content;
    }

    public MultipartFile getImage() {
        return image;
    }

    public NewPostForm(MultipartFile image, List<String> hashtag, String type, String model, String content) {
        this.image = image;
        this.hashtag = hashtag;
        this.type = type;
        this.model = model;
        this.content = content;
    }
    /*
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

     */
}
