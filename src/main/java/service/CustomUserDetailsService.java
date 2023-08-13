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

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsrRepository usrRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 아이디에 해당하는 사용자 정보 가져오기 (예: usrRepository.findByUserId(userId))
        String fetchedUserId = usrRepository.getCheckExistUserId(userId);
        String findPasswordFromDB = usrRepository.getUserPassword(userId);
        System.out.println("fetchedUserId : " + fetchedUserId);
        System.out.println("findPasswordFromDB : " + findPasswordFromDB);

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

        if (fetchedUserId.equals(userId)) {
            // 예시로 ROLE_USER 권한을 가진 사용자 생성
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println("authorities : " + authorities);

            //인증을 진행하는 코드 (Spring Security의 기본적인 로그인 인증 처리 코드)
            //인증 정보를 저장하지 않으면 사용자는 인증되지 않은 익명 사용자로 취급됩니다.
            //성공시 아래 로직으로 인증을 저장해야 반영 된다
            Authentication authentication = new UsernamePasswordAuthenticationToken(fetchedUserId, findPasswordFromDB, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 사용자 정보 반환
            return new CustomUserDetails(fetchedUserId, findPasswordFromDB, authorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + userId);
        }
    }

    // 사용자 정보를 데이터베이스에서 가져오는 메서드
//    private UsrDto getUserInfoFromRepository(String userId) {
//        // 실제로 데이터베이스에서 사용자 정보를 조회하는 로직을 구현해야 합니다.
//        // 예시로 아래와 같이 가정합니다.
//        return usrRepository.findByUserId(userId);
//    }

    // 사용자가 특정 역할을 가지고 있는지 확인하는 메서드
    private boolean userHasRole(String userId, String roleName) {
        // 실제로 사용자의 역할을 확인하는 로직을 구현해야 합니다.
        // 예시로 아래와 같이 가정합니다.
        return userId.startsWith(roleName);
    }
}