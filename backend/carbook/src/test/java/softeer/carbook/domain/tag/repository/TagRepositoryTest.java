package softeer.carbook.domain.tag.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import softeer.carbook.domain.tag.exception.HashtagNotExistException;
import softeer.carbook.domain.tag.model.Hashtag;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Sql("classpath:create_table.sql")
class TagRepositoryTest {

    private TagRepository tagRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        tagRepository = new TagRepository(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("delete from HASHTAG");
    }

    @Test
    @DisplayName("태그 이름으로 해시태그 조회")
    void findHashtagByName() {
        String tagName = "맑음";
        Hashtag hashtag = new Hashtag(tagName);
        int hashtagId = tagRepository.addHashtag(hashtag);

        Hashtag result = tagRepository.findHashtagByName(tagName);

        assertThat(result.getId()).isEqualTo(hashtagId);
        assertThat(result.getTag()).isEqualTo(tagName);
    }

    @Test
    @DisplayName("태그 이름으로 해시태그 조회 - 조회 결과가 없는 경우")
    void findHashtagByNameWithNoHashtag() {
        String tagName = "맑음";

        Throwable exception = assertThrows(HashtagNotExistException.class, () -> tagRepository.findHashtagByName(tagName));

        assertThat(exception.getMessage()).isEqualTo("ERROR: Hashtag not exist");
    }

//    @Test
//    void findHashtagsByPostId() {
//        Post post = new Post(1, "content", 1);
//        int postId = postRepository.addPost(post);
//
//        String tagName = "맑음";
//        Hashtag hashtag = new Hashtag(tagName);
//        int hashtagId = tagRepository.addHashtag(hashtag);
//
//        tagRepository.addPostHashtag(postId, hashtagId);
//
//        List<String> result = tagRepository.findHashtagsByPostId(postId);
//        assertThat(result.get(0)).isEqualTo(tagName);
//    }

    @ParameterizedTest
    @ValueSource(strings = {"맑", "맑음"})
    @DisplayName("키워드를 접두어로 하여 해시태그를 조회")
    void searchHashtagByPrefix(String keyword) {
        String tagName = "맑음";
        Hashtag hashtag = new Hashtag(tagName);
        int hashtagId = tagRepository.addHashtag(hashtag);

        List<Hashtag> result = tagRepository.searchHashtagByPrefix(keyword);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(hashtagId);
        assertThat(result.get(0).getTag()).isEqualTo(tagName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"%", "%%"})
    @DisplayName("'%'문자를 접두어로 하여 해시태그를 조회")
    void searchHashtagByPrefixWithWildCharPercentage(String keyword) {
        String tagName = "%%%길동";
        Hashtag hashtag = new Hashtag(tagName);
        int hashtagId = tagRepository.addHashtag(hashtag);

        List<Hashtag> result = tagRepository.searchHashtagByPrefix(keyword);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(hashtagId);
        assertThat(result.get(0).getTag()).isEqualTo(tagName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"_", "__"})
    @DisplayName("'_'를 접두어로 하여 해시태그를 조회")
    void searchHashtagByPrefixWithWildCharUnderBar(String keyword) {
        String tagName = "___길동";
        Hashtag hashtag = new Hashtag(tagName);
        int hashtagId = tagRepository.addHashtag(hashtag);

        List<Hashtag> result = tagRepository.searchHashtagByPrefix(keyword);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(hashtagId);
        assertThat(result.get(0).getTag()).isEqualTo(tagName);
    }
}