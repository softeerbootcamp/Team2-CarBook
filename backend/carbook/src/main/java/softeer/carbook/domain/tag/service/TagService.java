package softeer.carbook.domain.tag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.tag.dto.*;
import softeer.carbook.domain.tag.model.Hashtag;
import softeer.carbook.domain.tag.model.Model;
import softeer.carbook.domain.tag.model.Type;
import softeer.carbook.domain.tag.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagSearchResopnse searchAllTags(String keyword) {
        List<Type> types = tagRepository.searchTypeByPrefix(keyword);
        List<Model> models = tagRepository.searchModelByPrefix(keyword);
        List<Hashtag> hashtags = tagRepository.searchHashtagByPrefix(keyword);

        List<TagSearchResult> results = types.stream()
                .map(TagSearchResult::of)
                .collect(Collectors.toList());
        results.addAll(models.stream()
                .map(TagSearchResult::of)
                .collect(Collectors.toList()));
        results.addAll(hashtags.stream()
                .map(TagSearchResult::of)
                .collect(Collectors.toList()));

        return new TagSearchResopnse(results);
    }

    public HashtagSearchResponse searchHashTag(String keyword) {
        List<Hashtag> hashtags = tagRepository.searchHashtagByPrefix(keyword);

        return new HashtagSearchResponse(hashtags);
    }

    public TypesResponse findAllTypes() {
        return new TypesResponse(tagRepository.findAllTypes());
    }

    public ModelsResponse findAllModels() {
        return new ModelsResponse(tagRepository.findAllModels());
    }
}
