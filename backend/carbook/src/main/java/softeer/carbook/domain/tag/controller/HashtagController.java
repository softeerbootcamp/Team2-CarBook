package softeer.carbook.domain.tag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softeer.carbook.domain.tag.dto.HashtagSearchResponse;
import softeer.carbook.domain.tag.dto.ModelsResponse;
import softeer.carbook.domain.tag.dto.TagSearchResopnse;
import softeer.carbook.domain.tag.dto.TypesResponse;
import softeer.carbook.domain.tag.service.HashtagService;

@RestController
public class HashtagController {
    private final HashtagService hashtagService;

    @Autowired
    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    // 모든 태그 검색
    @GetMapping("/search/")
    public ResponseEntity<TagSearchResopnse> searchAllTags(@RequestParam String keyword) {
        TagSearchResopnse tagSearchResopnse = hashtagService.searchAllTags(keyword);
        return new ResponseEntity<>(tagSearchResopnse, HttpStatus.OK);
    }

    // 해시태그 검색
    @GetMapping("/search/hashtag/")
    public ResponseEntity<HashtagSearchResponse> searchHashtag(@RequestParam String keyword) {
        HashtagSearchResponse hashtagSearchResponse = hashtagService.searchHashTag(keyword);
        return new ResponseEntity<>(hashtagSearchResponse, HttpStatus.OK);
    }

    // 차량 종류 전체 조회
    @GetMapping("/type")
    public ResponseEntity<TypesResponse> getAllTypes() {
        TypesResponse typesResponse = hashtagService.findAllTypes();
        return new ResponseEntity<>(typesResponse, HttpStatus.OK);
    }

    // 차량 모델 전체 조회
    @GetMapping("/model")
    public ResponseEntity<ModelsResponse> getAllModels() {
        ModelsResponse modelsResponse = hashtagService.findAllModels();
        return new ResponseEntity<>(modelsResponse, HttpStatus.OK);
    }
}
