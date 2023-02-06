package softeer.carbook.domain.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.post.repository.PostRepository;
import softeer.carbook.domain.user.dto.Message;

@Service
public class PostService {
    PostRepository postRepository;
    private final int POST_COUNT = 10;

    @Autowired
    public PostService(PostRepository postRepository) { this.postRepository = postRepository; }

    /*
    public ResponseEntity<Message> getRecentPosts(int index){

    }

     */
}
