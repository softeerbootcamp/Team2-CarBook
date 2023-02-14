package softeer.carbook.domain.like.repository;

import org.springframework.stereotype.Repository;

@Repository
public class LikeRepository {
    public int findLikeCountByPostId(int postId) {
        return 1;
    }
}
