package softeer.carbook.domain.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import softeer.carbook.domain.post.dto.GuestPostsResponse;
import softeer.carbook.domain.post.dto.LoginPostsResponse;
import softeer.carbook.domain.post.dto.PostsSearchResponse;
import softeer.carbook.domain.post.service.PostService;
import softeer.carbook.domain.user.model.User;
import softeer.carbook.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PostController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/posts/m")
    public ResponseEntity<?> getPosts(@RequestParam int index, HttpServletRequest httpServletRequest){
        if (userService.isLogin(httpServletRequest))
            return getLoginPosts(index, httpServletRequest);
        return getGuestPosts(index);
    }

    private ResponseEntity<GuestPostsResponse> getGuestPosts(int index){
        return new ResponseEntity<>(postService.getRecentPosts(index), HttpStatus.OK);
    }


    private ResponseEntity<LoginPostsResponse> getLoginPosts(int index, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(postService.getRecentFollowerPosts(index, userService.findLoginedUser(httpServletRequest)), HttpStatus.OK);
    }

        // todo 로그인한 사람의 게시물 조회 ( 팔로우 )
            // mainpage url  + cookie

            // cookie를 통해 로그인 유저인지 확인 > userservice

            // 팔로잉 중인 유저 리스트 불러오기 > followservice

            // 리스트 별로 게시물 불러오기 > postservice

        // 일단 작성해놓음
        // 로그인하지 않은 사용자가 상세, 생성, 프로필 페이지 이동시 로그인페이지로
        // 상세 페이지 이동
        // 프로필 페이지 이동

        // 해시태그 검색 > 해시태그 폴더로

    // todo 해시태그의 게시물 조회
    @GetMapping("/posts/m/search")
    public ResponseEntity<PostsSearchResponse> searchPostsByTags(@RequestParam int index, @RequestParam String hashtag){
        return new ResponseEntity<>(postService.searchByTags(hashtag, index), HttpStatus.OK);
    }

    // 프로필 페이지

        // 일단 적어놓음
        // 로그인 하지 않은 사용자 로그인페이지로 이동

        // 해당 사용자 조회 > user 로

        // todo 해당 사용자의 게시물 조회
    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestParam String nickname, HttpServletRequest httpServletRequest){
        // 현재 접속한 사용자가 누구인가요?
        User loginUser = userService.findLoginedUser(httpServletRequest);

        // 현재 접속한 사용자가 해당 프로필 유저 인가요? >> 내 프로필 페이지
        if(loginUser.getNickname().equals(nickname)){

        }

        // 현재 접속한 사용자가 다른 사람의 프로필을 들어 갔나요 >> 타인의 프로필 페이지


        return new ResponseEntity<>(HttpStatus.OK);
    }

        // 닉네임 변경 ( 자신 프로필 페이지) > user 로

        // 비밀번호 변경 ( 자신 프로필 페이지 ) > user 로

        // 팔로워, 팔로잉 정보 받기 > follow 로

        // 팔로우, 언팔로우 기능 구현 ( 타인 프로필 페이지 ) > follow 로

        // 팔로워 리스트 조회 > follow 로

        // 팔로잉 리스트 조회 > follow 로

        // 로그아웃 구현 > user 로

    // 글 수정 페이지

        // todo 이전 글 정보 불러오기

        // todo 글 수정

    // 글 상세 페이지

        // todo 작성한 글 불러오기

        // 로그인한 사용자인지 > user 로

        // 좋아요           > like 로
        // 좋아요 취소       > like 로

    // 글 작성 페이지

        // todo 글 생성 로직

}
