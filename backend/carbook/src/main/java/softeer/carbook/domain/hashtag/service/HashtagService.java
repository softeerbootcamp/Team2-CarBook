package softeer.carbook.domain.hashtag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.hashtag.dto.HashtagSearchResponse;
import softeer.carbook.domain.hashtag.dto.TagSearchResult;
import softeer.carbook.domain.hashtag.dto.TagSearchResopnse;
import softeer.carbook.domain.hashtag.dto.TypeResponse;
import softeer.carbook.domain.hashtag.model.Hashtag;
import softeer.carbook.domain.hashtag.model.Model;
import softeer.carbook.domain.hashtag.model.Type;
import softeer.carbook.domain.hashtag.repository.HashtagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Autowired
    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public TagSearchResopnse searchAllTags(String keyword) {
        List<Type> types = hashtagRepository.searchTypeByPrefix(keyword);
        List<Model> models = hashtagRepository.searchModelByPrefix(keyword);
        List<Hashtag> hashtags = hashtagRepository.searchHashtagByPrefix(keyword);

        List<TagSearchResult> results = types.stream()
                .map(TagSearchResult::of)
                .collect(Collectors.toList());
        results.addAll(models.stream()
                .map(TagSearchResult::of)
                .collect(Collectors.toList()));
        results.addAll(hashtags.stream()
                .map(TagSearchResult::of)
                .collect(Collectors.toList()));

        return new TagSearchResopnse(results);
    }

    public HashtagSearchResponse searchHashTag(String keyword) {
        List<Hashtag> hashtags = hashtagRepository.searchHashtagByPrefix(keyword);

        return new HashtagSearchResponse(hashtags);
    }

//    public TypeSearchResponse searchType(String keyword) {
//        List<Type> types = hashtagRepository.searchTypeByPrefix(keyword);
//
//        return new TypeSearchResponse.TypeSearchResponseBuilder()
//                .types(types)
//                .build();
//    }
}
