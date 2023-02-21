package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrDto {

    private int id;

    private String name;

    private String email;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;

    private String password;

    private String view_yn;

    private String createDate;

}
