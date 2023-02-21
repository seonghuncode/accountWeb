package dto;


import lombok.Data;

@Data
public class UsrDto {
    private int id;
    private String name;
    private String email;
    private String userId;
    private String password;
    private String view_yn;
    private String createDate;

}
