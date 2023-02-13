package softeer.carbook.domain.tag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.tag.dto.*;
import softeer.carbook.domain.tag.model.Hashtag;
import softeer.carbook.domain.tag.model.Model;
import softeer.carbook.domain.tag.model.Type;
import softeer.carbook.domain.tag.repository.HashtagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Autowired
    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public TagSearchResopnse searchAllTags(String keyword) {
        List<Type> types = hashtagRepository.searchTypeByPrefix(keyword);
        List<Model> models = hashtagRepository.searchModelByPrefix(keyword);
        List<Hashtag> hashtags = hashtagRepository.searchHashtagByPrefix(keyword);

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
        List<Hashtag> hashtags = hashtagRepository.searchHashtagByPrefix(keyword);

        return new HashtagSearchResponse(hashtags);
    }

    public TypesResponse findAllTypes() {
        return new TypesResponse(hashtagRepository.findAllTypes());
    }

    public ModelsResponse findAllModels() {
        return new ModelsResponse(hashtagRepository.findAllModels());
    }
}
