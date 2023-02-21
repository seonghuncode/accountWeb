//package security;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration  //이 클래스를 통해 bean 등록이나 각종 설정을 하겠다는 표시
//@EnableWebSecurity //이 어노테이션이 spring security 설정할 클래스라고 정의
//@AllArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private MemberService memberService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {   //비밀번호 암호화 객체
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception
//    {
//        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
////        http.csrf().disable(); // 스프링 시큐리티를 추가하면 기본적으로 csrf에 대해 체크하기 때문에 POST가 정상적으로 수행되지 않는다.
////        //꼭 http.csrf() 를 disable() 해주어야 한다
//
//        http.authorizeRequests()
//                // 페이지 권한 설정
//                .antMatchers("/admin/**").hasRole("ADMIN")  ///admin 으로 시작하는 경로는 ADMIN 롤을 가진 사용자만 접근 가능합니다.(관리자페이지를 위한 곳)
//                .antMatchers("/usr/myinfo").hasRole("MEMBER")  ///user/myinfo 경로는 MEMBER 롤을 가진 사용자만 접근 가능합니다.
//                .antMatchers("/**").permitAll()  //모든 경로에 대해서는 권한없이 접근 가능합니다.
//                .and() // 로그인 설정
//                .formLogin()
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/usr/loginFn")
//                .permitAll()
//                .and() // 로그아웃 설정
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
//                .logoutSuccessUrl("/user/logout/result")
//                .invalidateHttpSession(true)
//                .and()
//                // 403 예외처리 핸들링
//                .exceptionHandling().accessDeniedPage("/user/denied");
//    }
//
//    //
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
//    }
//}