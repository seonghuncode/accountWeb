package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrDto {

    private int id;


    private String name;

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;


    @Length(min=2, max=5)
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;

    private String password;

    private String view_yn;

    private String createDate;

}
