package softeer.carbook.domain.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.hashtag.model.Hashtag;
import softeer.carbook.domain.hashtag.repository.HashtagRepository;
import softeer.carbook.domain.post.dto.GuestPostsResponse;
import softeer.carbook.domain.post.dto.PostsSearchResponse;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;
import softeer.carbook.domain.post.repository.ImageRepository;
import softeer.carbook.domain.post.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final HashtagRepository hashtagRepository;
    private final int POST_COUNT = 10;

    @Autowired
    public PostService(PostRepository postRepository, ImageRepository imageRepository, HashtagRepository hashtagRepository) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.hashtagRepository = hashtagRepository;
    }

    public GuestPostsResponse getRecentPosts(int index) {
        List<Post> posts = postRepository.getPosts(POST_COUNT, index);
        List<Image> images = new ArrayList<>();
        for (Post post : posts) {
            Image image = imageRepository.getImageByPostId(post.getId());
            images.add(image);
        }
        return new GuestPostsResponse(false, images);
    }

    public PostsSearchResponse searchByTags(String hashtags, int index) {
        String[] tagNames = hashtags.split("\\+");

        List<Integer> hashtagIds = new ArrayList<>();
        for (String tagName : tagNames) {
            Hashtag hashtag = hashtagRepository.findHashtagByName(tagName);
            hashtagIds.add(hashtag.getId());
        }

        List<Post> posts = postRepository.searchByHashtags(hashtagIds, POST_COUNT, index);

        List<Image> images = new ArrayList<>();
        for (Post post : posts) {
            Image image = imageRepository.getImageByPostId(post.getId());
            images.add(image);
        }

        return new PostsSearchResponse(images);
    }


    /*
    public LoginPostsResponse getRecentFollowerPosts(int index){

    }

     */

}
