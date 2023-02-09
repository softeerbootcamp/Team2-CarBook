package softeer.carbook.domain.follow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.follow.dto.FollowListResponse;
import softeer.carbook.domain.follow.dto.ModifyFollowInfoForm;
import softeer.carbook.domain.follow.repository.FollowRepository;
import softeer.carbook.domain.user.dto.Message;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Autowired
    public FollowService(
            FollowRepository followRepository,
            UserRepository userRepository
    ) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public Message modifyFollowInfo(User loginUser, String profileUserNickname) {
        // 프로필 사용자 정보 받아오기
        User profileUser = userRepository.findUserByNickname(profileUserNickname);


        Optional<Integer> followId = followRepository.findFollowId(loginUser.getId(), profileUser.getId());
        // 팔로우 여부 판단
        if(followId.isPresent()){
            // 언팔로우 진행
            followRepository.unFollow(followId.get());
            return new Message("Unfollow Success");
        }

        // 팔로우 진행
        followRepository.addFollow(loginUser.getId(), profileUser.getId());
        return new Message("Follow Success");
    }

    public FollowListResponse getFollowings(String nickname){
        return new FollowListResponse(userRepository.getFollowingNicknames(nickname));
    }

    public FollowListResponse getFollowers(String nickname){
        return new FollowListResponse(userRepository.getFollowerNicknames(nickname));
    }
}
