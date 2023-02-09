package softeer.carbook.domain.hashtag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.hashtag.dto.HashtagSearchResponse;
import softeer.carbook.domain.hashtag.model.Hashtag;
import softeer.carbook.domain.hashtag.repository.HashtagRepository;

import java.util.List;

@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Autowired
    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public HashtagSearchResponse searchHashTag(String keyword) {
        List<Hashtag> hashtags = hashtagRepository.searchHashtagByPrefix(keyword);

        return new HashtagSearchResponse.HashtagSearchResponseBuilder()
                .hashtags(hashtags)
                .build();
    }
}
