package softeer.carbook.global.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import softeer.carbook.domain.user.exception.NotLoginStatementException;
import softeer.carbook.domain.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("인터셉터 진입");
        checkLoginException(request.getSession(false));
        logger.info("예외 없음");
        return true;
        //return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void checkLoginException(HttpSession session){
        // 세션 없을 때, 다른 쿠키값 넘어왔을 때 체크
        if (session == null || session.getAttribute("user") == null) throw new NotLoginStatementException();
    }
}
