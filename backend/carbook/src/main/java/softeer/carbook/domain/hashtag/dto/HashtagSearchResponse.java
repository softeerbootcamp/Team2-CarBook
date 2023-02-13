package softeer.carbook.domain.hashtag.dto;

import softeer.carbook.domain.hashtag.model.Hashtag;

import java.util.List;

public class HashtagSearchResponse {
    private final List<Hashtag> hashtags;

    public HashtagSearchResponse(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }
}
