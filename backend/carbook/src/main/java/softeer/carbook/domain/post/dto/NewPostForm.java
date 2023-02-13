package softeer.carbook.domain.post.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class NewPostForm {
    @NotNull(message = "사진을 등록해 주세요.")
    private final MultipartFile image;

    @Size(max = 3)
    private final List<String> hashtag;

    @NotBlank(message = "차 타입을 입력해 주세요.")
    private final String type;

    @NotBlank(message = "차 모델을 입력해 주세요.")
    private final String model;

    private final String content;

    public MultipartFile getImage() {
        return image;
    }

    public List<String> getHashtag() {
        return hashtag;
    }

    public String getModel() {
        return model;
    }

    public String getContent() {
        return content;
    }

    public NewPostForm(MultipartFile image, List<String> hashtag, String type, String model, String content) {
        this.image = image;
        this.hashtag = hashtag;
        this.type = type;
        this.model = model;
        this.content = content;
    }

}
