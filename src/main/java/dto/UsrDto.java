package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrDto {

    private int id;

    @NotBlank(message = "이름을 입력해 주세요.")
    @Size(max = 10, message = "이름은 10글자 이내로 작성해 주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "아이디를 입력해 주세요.")
    @Length(min = 6,max = 10, message = "아이디는 6자리 ~ 10자리 이하로입력해 주세요.")
    private String userId;

    @Length(min = 8,max = 15, message = "비밀번호는 숫자,문자,특수문자를 한자리 이상 포함한 8 ~ 15자리로 입력해 주세요.")
    private String password;

    @Length(min = 8,max = 15, message = "비밀번호는 숫자,문자,특수문자를 한자리 이상 포함한 8 ~ 15자리로 입력해 주세요.")
    private String checkPassword;

    private String view_yn;

    private String createDate;




}
