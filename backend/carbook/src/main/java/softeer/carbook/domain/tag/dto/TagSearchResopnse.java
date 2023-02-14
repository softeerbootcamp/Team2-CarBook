package softeer.carbook.domain.tag.dto;

import java.util.List;

public class TagSearchResopnse {
    private final List<TagSearchResult> keywords;

    public TagSearchResopnse(List<TagSearchResult> keywords) {
        this.keywords = keywords;
    }

    public List<TagSearchResult> getKeywords() {
        return keywords;
    }
}
