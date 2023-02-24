package util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity // spring security filter가 Spring filter chain 에 등록
@RequiredArgsConstructor  //CustomUserDetailsService 생성자 주입을 위한 lombok
@EnableGlobalMethodSecurity(prePostEnabled = true) //  @PreAuthorize("isAuthenticated()")어노테이션을 사용하기 위해 사용(공식 같은 것)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * password 암호화 메소드. 추후 계정 생성 시, 사용함
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{       // Spring Security 설정
        http.csrf().disable();      // csrf 비활성화

        // URL에 따른 접근 제한 처리
        http.authorizeRequests()
                .antMatchers("/**").authenticated()        // URL user : 인증이 되어야 함
                //.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")      // URL manager : 권한 'ROLE_ADMIN', 'ROLE_MANAGER'가 있어야함
               // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")         // URL admin : 권한 'ROLE_ADMIN'이 있어야함
                .anyRequest().permitAll()      // 위에 명시되지 않은 URL 은 로그인 및 권한 검사 X
                .and()
                .formLogin()
                .loginPage("/usr/loginForm")       // formLogin이 필요한 경우, /login 으로 보낸다.
                .loginProcessingUrl("/usr/loginFn")   // login 주소가 호출이 되면 시큐리티가 낚이채서 대신 로그인을 진행
                .defaultSuccessUrl("/");        // login 성공 시, 보내줄 기본 url


    }
}
