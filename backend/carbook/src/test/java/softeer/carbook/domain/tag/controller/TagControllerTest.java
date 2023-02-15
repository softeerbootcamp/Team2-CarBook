package softeer.carbook.domain.tag.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import softeer.carbook.domain.tag.dto.*;
import softeer.carbook.domain.tag.model.Hashtag;
import softeer.carbook.domain.tag.model.Model;
import softeer.carbook.domain.tag.model.Type;
import softeer.carbook.domain.tag.service.TagService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Test
    @DisplayName("모든 태그 검색 테스트")
    void searchAllTags() throws Exception {
        //given
        TagSearchResopnse response = new TagSearchResopnse(new ArrayList<>(List.of(
                new TagSearchResult(1, "hashtag", "맑음")
        )));
        given(tagService.searchAllTags(anyString())).willReturn(response);

        // when & then
        mockMvc.perform(get("/search/?keyword=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keywords").exists())
                .andExpect(jsonPath("$.keywords[0].id").value(1))
                .andExpect(jsonPath("$.keywords[0].category").value("hashtag"))
                .andExpect(jsonPath("$.keywords[0].tag").value("맑음"));
    }

    @Test
    @DisplayName("해시태그 검색 테스트")
    void searchHashtag() throws Exception {
        //given
        HashtagSearchResponse response = new HashtagSearchResponse(new ArrayList<>(List.of(
                new Hashtag(1, "맑음")
        )));
        given(tagService.searchHashTag(anyString())).willReturn(response);

        // when & then
        mockMvc.perform(get("/search/hashtag/?keyword=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hashtags").exists())
                .andExpect(jsonPath("$.hashtags[0].id").value(1))
                .andExpect(jsonPath("$.hashtags[0].tag").value("맑음"));
    }

    @Test
    @DisplayName("차량 종류 전체 조회 테스트")
    void getAllTypes() throws Exception {
        //given
        TypesResponse response = new TypesResponse(new ArrayList<>(List.of(
                new Type(1, "승용"), new Type(2, "SUV")
        )));
        given(tagService.findAllTypes()).willReturn(response);

        // when & then
        mockMvc.perform(get("/type"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.types").exists())
                .andExpect(jsonPath("$.types[0].id").value(1))
                .andExpect(jsonPath("$.types[0].tag").value("승용"))
                .andExpect(jsonPath("$.types[1].id").value(2))
                .andExpect(jsonPath("$.types[1].tag").value("SUV"));
    }

    @Test
    @DisplayName("차량 타입 전체 조회 테스트")
    void getAllModels() throws Exception {
        //given
        ModelsResponse response = new ModelsResponse(new ArrayList<>(List.of(
                new Model(1, 1, "아이오닉 6"), new Model(2, 2, "아이오닉 5")
        )));
        given(tagService.findAllModels()).willReturn(response);

        // when & then
        mockMvc.perform(get("/model"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.models").exists())
                .andExpect(jsonPath("$.models[0].id").value(1))
                .andExpect(jsonPath("$.models[0].typeId").value(1))
                .andExpect(jsonPath("$.models[0].tag").value("아이오닉 6"))
                .andExpect(jsonPath("$.models[1].id").value(2))
                .andExpect(jsonPath("$.models[1].typeId").value(2))
                .andExpect(jsonPath("$.models[1].tag").value("아이오닉 5"));
    }

}