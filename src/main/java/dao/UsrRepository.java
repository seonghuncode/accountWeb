package dao;

import dto.UsrDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsrRepository {
    public UsrDto getMemberByLoginId(String userId);

    public String getCheckExistEmail(String email);

    public String getCheckExistUserId(String userId);

    public void join(UsrDto usrDto);

    public String getUserPassword(String userId); //아이디를 넘겨주면 해당 아이디의 비밀번호를 찾아서 넘겨준다.

    public String findUserNameByUserId(String userId);

    public List<UsrDto> getAllUserFromDB();
}
