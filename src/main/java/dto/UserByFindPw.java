package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserByFindPw {

    private String name;
    private String userId;
    private String email;
    private int userPk;
    private String temporaryPassword;

}
