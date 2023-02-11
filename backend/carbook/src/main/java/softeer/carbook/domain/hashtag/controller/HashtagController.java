package softeer.carbook.domain.hashtag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softeer.carbook.domain.hashtag.dto.HashtagSearchResponse;
import softeer.carbook.domain.hashtag.dto.TypeSearchResponse;
import softeer.carbook.domain.hashtag.service.HashtagService;

@RestController
public class HashtagController {
    private final HashtagService hashtagService;

    @Autowired
    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    // 모든 태그 검색

    // 해시태그 검색
    @GetMapping("/search/hashtag/")
    public ResponseEntity<HashtagSearchResponse> searchHashtag(@RequestParam String keyword) {
        HashtagSearchResponse hashtagSearchResponse = hashtagService.searchHashTag(keyword);
        return new ResponseEntity<>(hashtagSearchResponse, HttpStatus.OK);
    }

    // 차량 종류 태그 검색
    @GetMapping("/search/type/")
    public ResponseEntity<TypeSearchResponse> searchType(@RequestParam String keyword) {
        TypeSearchResponse typeSearchResponse = hashtagService.searchType(keyword);
        return new ResponseEntity<>(typeSearchResponse, HttpStatus.OK);
    }
}
