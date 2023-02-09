package softeer.carbook.domain.hashtag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import softeer.carbook.domain.hashtag.dto.HashtagSearchResponse;
import softeer.carbook.domain.hashtag.model.Hashtag;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HashtagServiceTest {

    @Autowired
    HashtagService hashtagService;

    @Test
    @DisplayName("해시태그 검색 기능 테스트")
    void searchHashTag() {
        String keyword = "맑음";
        int expectedId = 1;

        HashtagSearchResponse response = hashtagService.searchHashTag(keyword);
        List<Hashtag> hashtags = response.getHashtags();
        Hashtag hashtag = hashtags.get(0);

        assertThat(hashtags.size()).isEqualTo(1);
        assertThat(hashtag.getId()).isEqualTo(expectedId);
        assertThat(hashtag.getTag()).isEqualTo(keyword);
    }
}