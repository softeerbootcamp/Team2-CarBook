package softeer.carbook.domain.hashtag.dto;

import softeer.carbook.domain.hashtag.model.Hashtag;
import softeer.carbook.domain.hashtag.model.Model;
import softeer.carbook.domain.hashtag.model.Type;

public class TagSearchResult {
    private final int id;
    private final String category;
    private final String tag;

    public TagSearchResult(int id, String category, String tag) {
        this.id = id;
        this.category = category;
        this.tag = tag;
    }

    // Entity -> DTO
    public static TagSearchResult of(Type type) {
        return new TagSearchResult(type.getId(), "type", type.getTag());
    }

    public static TagSearchResult of(Model model) {
        return new TagSearchResult(model.getId(), "model", model.getTag());
    }

    public static TagSearchResult of(Hashtag hashtag) {
        return new TagSearchResult(hashtag.getId(), "hashtag", hashtag.getTag());
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }
}
