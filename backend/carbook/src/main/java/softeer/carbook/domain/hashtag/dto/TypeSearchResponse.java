package softeer.carbook.domain.hashtag.dto;

import softeer.carbook.domain.hashtag.model.Type;

import java.util.List;

public class TypeSearchResponse {
    private List<Type> types;

    public TypeSearchResponse(TypeSearchResponseBuilder builder) {
        this.types = builder.types;
    }

    public List<Type> getTypes() {
        return types;
    }

    public static class TypeSearchResponseBuilder {

        private List<Type> types;

        public TypeSearchResponseBuilder() {
        }

        public TypeSearchResponseBuilder types(List<Type> types) {
            this.types = types;
            return this;
        }

        public TypeSearchResponse build() {
            return new TypeSearchResponse(this);
        }
    }
}
