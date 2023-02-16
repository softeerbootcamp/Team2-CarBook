package softeer.carbook.domain.tag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import softeer.carbook.domain.tag.dto.*;
import softeer.carbook.domain.tag.model.Hashtag;
import softeer.carbook.domain.tag.model.Model;
import softeer.carbook.domain.tag.model.Type;
import softeer.carbook.domain.tag.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    private final List<TagSearchResult> keywords = new ArrayList<>(List.of(
            new TagSearchResult(1, "type", "승용"),
            new TagSearchResult(1, "model", "아이오닉"),
            new TagSearchResult(1, "hashtag", "맑음")
    ));

    @Test
    @DisplayName("모든 태그 검색 기능 테스트 - 검색 결과가 있는 경우")
    void searchAllTags() {
        // given
        String keyword = "keyword";
        given(tagRepository.searchTypeByPrefix(keyword)).willReturn(new ArrayList<>(List.of(
                new Type(1, "승용")
        )));
        given(tagRepository.searchModelByPrefix(keyword)).willReturn(new ArrayList<>(List.of(
                new Model(1, 1, "아이오닉")
        )));
        given(tagRepository.searchHashtagByPrefix(keyword)).willReturn(new ArrayList<>(List.of(
                new Hashtag(1, "맑음")
        )));

        // when
        TagSearchResopnse response = tagService.searchAllTags(keyword);

        // then
        assertThat(response.getKeywords()).usingRecursiveComparison().isEqualTo(keywords);
        verify(tagRepository).searchTypeByPrefix(keyword);
        verify(tagRepository).searchModelByPrefix(keyword);
        verify(tagRepository).searchHashtagByPrefix(keyword);
    }

    @Test
    @DisplayName("모든 태그 검색 기능 테스트 - 검색 결과가 없는 경우")
    void searchAllTagsWithNoResult() {
        // given
        String keyword = "keyword";
        given(tagRepository.searchTypeByPrefix(keyword)).willReturn(new ArrayList<>());
        given(tagRepository.searchModelByPrefix(keyword)).willReturn(new ArrayList<>());
        given(tagRepository.searchHashtagByPrefix(keyword)).willReturn(new ArrayList<>());

        // when
        TagSearchResopnse response = tagService.searchAllTags(keyword);

        // then
        assertThat(response.getKeywords()).usingRecursiveComparison().isEqualTo(new ArrayList<>());
        verify(tagRepository).searchTypeByPrefix(keyword);
        verify(tagRepository).searchModelByPrefix(keyword);
        verify(tagRepository).searchHashtagByPrefix(keyword);
    }

    @Test
    @DisplayName("해시태그 검색 기능 테스트 - 검색 결과가 있는 경우")
    void searchHashTag() {
        // given
        String keyword = "keyword";
        given(tagRepository.searchHashtagByPrefix(keyword)).willReturn(new ArrayList<>(List.of(
                new Hashtag(1, "맑음")
        )));

        // when
        HashtagSearchResponse response = tagService.searchHashTag(keyword);

        // then
        List<Hashtag> result = response.getHashtags();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(0).getTag()).isEqualTo("맑음");
        verify(tagRepository).searchHashtagByPrefix(keyword);
    }

    @Test
    @DisplayName("해시태그 검색 기능 테스트 - 검색 결과가 없는 경우")
    void searchHashTagWithNoResult() {
        // given
        String keyword = "keyword";
        given(tagRepository.searchHashtagByPrefix(keyword)).willReturn(new ArrayList<>());

        // when
        HashtagSearchResponse response = tagService.searchHashTag(keyword);

        // then
        List<Hashtag> result = response.getHashtags();
        assertThat(result.size()).isEqualTo(0);
        verify(tagRepository).searchHashtagByPrefix(keyword);
    }

    @Test
    @DisplayName("모든 차량 종류 조회 테스트")
    void findAllTypes() {
        //given
        List<Type> expectedResult = new ArrayList<>(List.of(
                new Type(1, "승용"), new Type(2, "SUV")));
        given(tagRepository.findAllTypes()).willReturn(expectedResult);

        //when
        TypesResponse response = tagService.findAllTypes();

        //then
        List<Type> result = response.getTypes();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
        verify(tagRepository).findAllTypes();
    }

    @Test
    @DisplayName("모든 차량 모델 조회 테스트")
    void findAllModels() {
        //given
        List<Model> expectedResult = new ArrayList<>(List.of(
                new Model(1, 1, "쏘나타"), new Model(2, 2, "아이오닉")));
        given(tagRepository.findAllModels()).willReturn(expectedResult);

        //when
        ModelsResponse response = tagService.findAllModels();

        //then
        List<Model> result = response.getModels();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
        verify(tagRepository).findAllModels();
    }
}
