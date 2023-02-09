package softeer.carbook.domain.hashtag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(strings = {"맑", "맑음"})
    @DisplayName("해시태그 검색 기능 테스트")
    void searchHashTag(String keyword) {
        HashtagSearchResponse response = hashtagService.searchHashTag(keyword);

        List<Hashtag> result = response.getHashtags();
        Hashtag hashtag = result.get(0);
        assertThat(result.size()).isEqualTo(1);
        assertThat(hashtag.getId()).isEqualTo(1);
        assertThat(hashtag.getTag()).isEqualTo("맑음");
    }
}