package softeer.carbook.domain.tag.dto;

import softeer.carbook.domain.tag.model.Hashtag;

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
