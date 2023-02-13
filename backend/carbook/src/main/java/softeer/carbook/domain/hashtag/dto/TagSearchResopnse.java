package softeer.carbook.domain.hashtag.dto;

import java.util.List;

public class TagSearchResopnse {
    private List<TagSearchResult> keywords;

    public TagSearchResopnse(TagSearchResponseBuilder builder) {
        this.keywords = builder.keywords;
    }

    public List<TagSearchResult> getKeywords() {
        return keywords;
    }

    public static class TagSearchResponseBuilder {

        private List<TagSearchResult> keywords;

        public TagSearchResponseBuilder() {
        }

        public TagSearchResponseBuilder keywords(List<TagSearchResult> keywords) {
            this.keywords = keywords;
            return this;
        }

        public TagSearchResopnse build() {
            return new TagSearchResopnse(this);
        }

    }
}
