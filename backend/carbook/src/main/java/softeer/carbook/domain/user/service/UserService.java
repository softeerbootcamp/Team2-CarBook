package softeer.carbook.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.carbook.domain.user.dto.SignupForm;

@Service
public class UserService {
    @Autowired
    public static void signup(SignupForm signupForm){

    }

    private static boolean isDuplicated(){
        boolean isDuplicated = false;

        return isDuplicated;
    }

}
