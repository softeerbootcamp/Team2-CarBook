package softeer.carbook.domain.like.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.like.repository.LikeRepository;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.global.dto.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository){
        this.likeRepository = likeRepository;
    }

    public Message modifyLikeInfo(int userId, int postId) {

        Optional<Integer> likeId = likeRepository.findLikeByUserIdAndPostId(userId, postId);
        // 좋아요 여부 판단
        if(likeId.isPresent()){
            // 좋아요 취소 진행
            likeRepository.unLike(likeId.get(), postId);
            return new Message("UnLike Success");
        }

        // 팔로우 진행
        likeRepository.addLike(userId, postId);
        return new Message("Like Success");
    }
}
