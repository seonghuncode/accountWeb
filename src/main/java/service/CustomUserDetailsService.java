package service;

import dao.UsrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import util.CustomUserDetails;

import java.util.ArrayList;
import java.util.Collection;


//사용자를 인증하고 사용자 정보와 권한을 반환하는 역할을 수행
@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UsrRepository usrRepository;

    //기본 정해진 틀로 String userId만 받아야 한다?
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 아이디에 해당하는 사용자 정보 가져오기 (예: usrRepository.findByUserId(userId))
        //파라미터로 받은(사용자가 입력한) 아이디를 갖고 DB에서 일치하는 아이디와 비밀번호를 찾는다.
        String fetchedUserId = usrRepository.getCheckExistUserId(userId);
        String findPasswordFromDB = usrRepository.getUserPassword(userId);
//        System.out.println("fetchedUserId : " + fetchedUserId);
//        System.out.println("findPasswordFromDB : " + findPasswordFromDB);

//        System.out.println("권한인증, 부여 부분");

        //일치 하는 아이디가 없을 경우
        if (fetchedUserId == null) {
            throw new UsernameNotFoundException("User not found");
        }

//        // 사용자의 권한을 확인하고 권한 부여
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if (userHasRole(fetchedUserId, "ROLE_USER")) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        }

//        // 아이디가 "11"로 시작하는 사용자에게만 ROLE_USER 권한 부여
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if (fetchedUserId.startsWith("11")) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        }

        //사용자가 입력한 아이디와 DB에 있는 아이디가 일치할 경우
        if (fetchedUserId.equals(userId)) {
            // 예시로 ROLE_USER 권한을 가진 사용자 생성
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//            System.out.println("authorities : " + authorities); //권한에 대한 결과

            //중요!!
            //인증을 진행하는 코드 (Spring Security의 기본적인 로그인 인증 처리 코드)
            //인증 정보를 저장하지 않으면 사용자는 인증되지 않은 익명 사용자로 취급됩니다.
            //성공시 아래 로직으로 인증을 저장해야 반영 된다
            Authentication authentication = new UsernamePasswordAuthenticationToken(fetchedUserId, findPasswordFromDB, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println("등록된 권한 : "  + authentication);

//            System.out.println("권한 인증 및 부여됨");

            // 사용자 정보 반환
            return new CustomUserDetails(fetchedUserId, findPasswordFromDB, authorities);
        } else {
//            System.out.println("권한이 없음");
            throw new UsernameNotFoundException("User not found with username: " + userId);
        }
    }


    // 사용자가 특정 역할을 가지고 있는지 확인하는 메서드
    private boolean userHasRole(String userId, String roleName) {
        // 실제로 사용자의 역할을 확인하는 로직을 구현해야 합니다.
        // 예시로 아래와 같이 가정합니다.
        return userId.startsWith(roleName);
    }


}