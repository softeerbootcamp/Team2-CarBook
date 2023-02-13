package softeer.carbook.domain.hashtag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.hashtag.dto.HashtagSearchResponse;
import softeer.carbook.domain.hashtag.dto.Keyword;
import softeer.carbook.domain.hashtag.dto.TagSearchResopnse;
import softeer.carbook.domain.hashtag.dto.TypeSearchResponse;
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

        List<Keyword> keywords = types.stream()
                .map(Keyword::of)
                .collect(Collectors.toList());
        keywords.addAll(models.stream()
                .map(Keyword::of)
                .collect(Collectors.toList()));
        keywords.addAll(hashtags.stream()
                .map(Keyword::of)
                .collect(Collectors.toList()));

        return new TagSearchResopnse.TagSearchResponseBuilder()
                .keywords(keywords)
                .build();
    }

    public HashtagSearchResponse searchHashTag(String keyword) {
        List<Hashtag> hashtags = hashtagRepository.searchHashtagByPrefix(keyword);

        return new HashtagSearchResponse.HashtagSearchResponseBuilder()
                .hashtags(hashtags)
                .build();
    }

//    public TypeSearchResponse searchType(String keyword) {
//        List<Type> types = hashtagRepository.searchTypeByPrefix(keyword);
//
//        return new TypeSearchResponse.TypeSearchResponseBuilder()
//                .types(types)
//                .build();
//    }
}
