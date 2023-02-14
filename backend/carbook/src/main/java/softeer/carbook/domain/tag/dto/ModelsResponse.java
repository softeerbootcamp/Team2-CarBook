package softeer.carbook.domain.tag.dto;

import softeer.carbook.domain.tag.model.Model;

import java.util.List;

public class ModelsResponse {
    private final List<Model> models;

    public ModelsResponse(List<Model> models) {
        this.models = models;
    }

    public List<Model> getModels() {
        return models;
    }

}
