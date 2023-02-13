package softeer.carbook.domain.hashtag.dto;

import java.util.List;

public class TagSearchResopnse {
    private List<Keyword> keywords;

    public TagSearchResopnse(TagSearchResponseBuilder builder) {
        this.keywords = builder.keywords;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public static class TagSearchResponseBuilder {

        private List<Keyword> keywords;

        public TagSearchResponseBuilder() {
        }

        public TagSearchResponseBuilder keywords(List<Keyword> keywords) {
            this.keywords = keywords;
            return this;
        }

        public TagSearchResopnse build() {
            return new TagSearchResopnse(this);
        }

    }
}
