package softeer.carbook.domain.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.post.dto.GuestPostsResponse;
import softeer.carbook.domain.post.model.Image;
import softeer.carbook.domain.post.model.Post;
import softeer.carbook.domain.post.repository.ImageRepository;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.user.dto.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    PostRepository postRepository;
    ImageRepository imageRepository;
    private final int POST_COUNT = 10;

    @Autowired
    public PostService(PostRepository postRepository) { this.postRepository = postRepository; }

    public GuestPostsResponse getRecentPosts(int index){
        List<Post> posts = postRepository.getPosts(POST_COUNT, index);
        List<Image> images = new ArrayList<>();
        for (Post post: posts){
            Image image = imageRepository.getImageByPostId(post.getId());
            images.add(image);
        }
        return new GuestPostsResponse(false, images);
    }

}
