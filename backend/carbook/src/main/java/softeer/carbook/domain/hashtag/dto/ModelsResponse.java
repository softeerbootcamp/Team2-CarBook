package softeer.carbook.domain.hashtag.dto;

import softeer.carbook.domain.hashtag.model.Model;

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
