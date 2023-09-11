package util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLoginPage implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        System.out.println("[preHandle][" + request + "]" + request.getRequestURI() + "[" + request.getMethod()
//                + "]" + request.getRequestURI());
        //System.out.println("preHandle1");


//        HttpSession session = request.getSession(); //1. 세션을 받아오는 과정
//        System.out.println("세션 : " + session);
//
//        if (session == null) {
//            response.sendRedirect("/usr/loginForm");
//        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle1");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        System.out.println("afterCompletion1");

        //로그인 페이지로 들어올때 세션이 만료되었고 + 권한이 없을 경우 경고 메시지를 띄우도록
        //권한이 부여되지 않았을 경우 권한 이름 : ROLE_ANONYMOUS
        //만약 사용자가 부여받은 권한이 없고 기본 권한이라면 권한 만료페이지 이동
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("현재 권한 : " + authentication.getAuthorities());
        boolean hasUserRole = false;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ANONYMOUS")) {
                hasUserRole = true;
                break;
            }
        }
        System.out.println(hasUserRole);
        if (hasUserRole) {
            System.out.println("현재 권한을 갖고 있지 않습니다." + request.getContextPath() + "/usr/sessionExpired");
            response.sendRedirect(request.getContextPath() + "/usr/sessionExpired");
        }
    }

}