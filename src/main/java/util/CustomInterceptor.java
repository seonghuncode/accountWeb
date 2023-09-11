package util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class CustomInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        System.out.println("[preHandle][" + request + "]" + request.getRequestURI() + "[" + request.getMethod()
//                + "]" + request.getRequestURI());
//        System.out.println("preHandle1");
//
//        //authentication는 권한 목록 컬렉션을 반환하기 때문에 문자열과 비교할 수 없다, 권한이 있는지 없는지 확인하여 처리 하는 로직
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        System.out.println("소유하고 있는 권한 이름 : " + authentication.getAuthorities());
//        boolean hasUserRole = false;
//        for (GrantedAuthority authority : authentication.getAuthorities()) {
//            if (authority.getAuthority().equals("ROLE_USER")) {
//                hasUserRole = true;
//                break;
//            }
//        }
//        System.out.println(hasUserRole);
//        if (!hasUserRole) {
////            System.out.println("현재 권한을 갖고 있지 않습니다.");
//            response.sendRedirect("/usr/sessionExpired");
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
//        System.out.println("afterCompletion1");
    }

}
