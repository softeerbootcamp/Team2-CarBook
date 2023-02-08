package softeer.carbook.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.post.dto.PostsSearchResponse;
import softeer.carbook.domain.post.model.Image;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    @DisplayName("해시태그를 통한 게시물 검색 기능 테스트")
    void searchByTags() {
        String hashtags = "맑음+흐림";

        PostsSearchResponse response = postService.searchByTags(hashtags, 0);
        List<Image> result = response.getImages();

        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get(0).getPostId()).isEqualTo(8);
        assertThat(result.get(1).getPostId()).isEqualTo(6);
        assertThat(result.get(2).getPostId()).isEqualTo(3);
        assertThat(result.get(3).getPostId()).isEqualTo(2);
        assertThat(result.get(0).getImageUrl()).isEqualTo("/eighth/image.jpg");
        assertThat(result.get(1).getImageUrl()).isEqualTo("/sixth/image.jpg");
        assertThat(result.get(2).getImageUrl()).isEqualTo("/third/image.jpg");
        assertThat(result.get(3).getImageUrl()).isEqualTo("/second/image.jpg");
    }
}
