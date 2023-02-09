package softeer.carbook.domain.hashtag.dto;

import softeer.carbook.domain.hashtag.model.Hashtag;

import java.util.List;

public class HashtagSearchResponse {
    private List<Hashtag> hashtags;

    public HashtagSearchResponse(HashtagSearchResponseBuilder builder) {
        this.hashtags = builder.hashtags;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public static class HashtagSearchResponseBuilder {

        private List<Hashtag> hashtags;

        public HashtagSearchResponseBuilder() {
        }

        public HashtagSearchResponseBuilder hashtags(List<Hashtag> hashtags) {
            this.hashtags = hashtags;
            return this;
        }

        public HashtagSearchResponse build() {
            return new HashtagSearchResponse(this);
        }
    }
}
