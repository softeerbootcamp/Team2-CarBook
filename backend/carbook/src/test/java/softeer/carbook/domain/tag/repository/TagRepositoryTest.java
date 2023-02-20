package softeer.carbook.domain.tag.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.tag.exception.HashtagNotExistException;
import softeer.carbook.domain.tag.model.Hashtag;
import softeer.carbook.domain.tag.model.Model;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Sql("classpath:create_table.sql")
@Sql("classpath:create_data.sql")
class TagRepositoryTest {

    private TagRepository tagRepository;
    private PostRepository postRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        tagRepository = new TagRepository(dataSource);
        postRepository = new PostRepository(dataSource);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,테스트태그1", "2,테스트태그2", "3,테스트태그3"})
    @DisplayName("태그 이름으로 해시태그 조회 테스트")
    void findHashtagByName(int hashtagId, String tagName) {
        // given & when
        Hashtag result = tagRepository.findHashtagByName(tagName);

        // then
        assertThat(result.getId()).isEqualTo(hashtagId);
        assertThat(result.getTag()).isEqualTo(tagName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"맑음", "흐림"})
    @DisplayName("태그 이름으로 해시태그 조회 테스트 - 조회 결과가 없는 경우")
    void findHashtagByNameWithNoHashtag(String tagName) {
        // given & when
        Throwable exception = assertThrows(HashtagNotExistException.class, () -> tagRepository.findHashtagByName(tagName));

        // then
        assertThat(exception.getMessage()).isEqualTo("ERROR: Hashtag not exist");
    }

    @Test
    @DisplayName("게시물 id로 해시태그 조회 테스트")
    void findHashtagsByPostId() {
        // given
        int postId = 1;

        // when
        List<String> result = tagRepository.findHashtagsByPostId(postId);

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0)).isEqualTo("테스트태그1");
        assertThat(result.get(1)).isEqualTo("테스트태그2");
        assertThat(result.get(2)).isEqualTo("테스트태그3");
    }

    @ParameterizedTest
    @ValueSource(strings = {"맑", "맑음"})
    @DisplayName("키워드를 접두어로 한 해시태그 조회 테스트")
    void searchHashtagByPrefix(String keyword) {
        // given
        String tagName = "맑음";
        Hashtag hashtag = new Hashtag(tagName);
        int hashtagId = tagRepository.addHashtag(hashtag);

        // when
        List<Hashtag> result = tagRepository.searchHashtagByPrefix(keyword);

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(hashtagId);
        assertThat(result.get(0).getTag()).isEqualTo(tagName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"%", "%%"})
    @DisplayName("'%'문자를 접두어로 한 해시태그 조회 테스트")
    void searchHashtagByPrefixWithWildCharPercentage(String keyword) {
        // given
        String tagName = "%%%길동";
        Hashtag hashtag = new Hashtag(tagName);
        int hashtagId = tagRepository.addHashtag(hashtag);

        // when
        List<Hashtag> result = tagRepository.searchHashtagByPrefix(keyword);

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(hashtagId);
        assertThat(result.get(0).getTag()).isEqualTo(tagName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"_", "__"})
    @DisplayName("'_'를 접두어로 한 해시태그 조회 테스트")
    void searchHashtagByPrefixWithWildCharUnderBar(String keyword) {
        // given
        String tagName = "___길동";
        Hashtag hashtag = new Hashtag(tagName);
        int hashtagId = tagRepository.addHashtag(hashtag);

        // when
        List<Hashtag> result = tagRepository.searchHashtagByPrefix(keyword);

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(hashtagId);
        assertThat(result.get(0).getTag()).isEqualTo(tagName);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1,아이오닉 6", "2,1,아이오닉 5", "3,1,넥쏘"})
    @DisplayName("모델 이름으로 모델 태그 조회 테스트")
    void findModelByName(int modelId, int typeId, String tagName) {
        // given & when
        Model result = tagRepository.findModelByName(tagName);

        // then
        assertThat(result.getId()).isEqualTo(modelId);
        assertThat(result.getTypeId()).isEqualTo(typeId);
        assertThat(result.getTag()).isEqualTo(tagName);
    }
}