package softeer.carbook.domain.tag.dto;

import softeer.carbook.domain.tag.model.Type;

import java.util.List;

public class TypesResponse {
    private final List<Type> types;

    public TypesResponse(List<Type> types) {
        this.types = types;
    }

    public List<Type> getTypes() {
        return types;
    }

}
