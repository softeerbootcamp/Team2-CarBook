package softeer.carbook.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.user.dto.SignupForm;
import softeer.carbook.domain.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void signup(SignupForm signupForm){
        if(isDuplicated(signupForm)) return; // 중복일 경우 처리
        //userRepository.addUser(signupForm);
    }

    private boolean isDuplicated(SignupForm signupForm){
        boolean isDuplicated = false;
        //isDuplicated = userRepository.isEmailDuplicated(signupForm.getEmail());
        //isDuplicated = userRepository.isNicknameDuplicated(signupForm.getNickname());
        return isDuplicated;
    }

}
