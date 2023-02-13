package softeer.carbook.domain.hashtag.dto;

import softeer.carbook.domain.hashtag.model.Hashtag;
import softeer.carbook.domain.hashtag.model.Model;
import softeer.carbook.domain.hashtag.model.Type;

public class Keyword {
    private final int id;
    private final String category;
    private final String tag;

    public Keyword(int id, String category, String tag) {
        this.id = id;
        this.category = category;
        this.tag = tag;
    }

    // Entity -> DTO
    public static Keyword of(Type type) {
        return new Keyword(type.getId(), "type", type.getTag());
    }

    public static Keyword of(Model model) {
        return new Keyword(model.getId(), "model", model.getTag());
    }

    public static Keyword of(Hashtag hashtag) {
        return new Keyword(hashtag.getId(), "hashtag", hashtag.getTag());
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
