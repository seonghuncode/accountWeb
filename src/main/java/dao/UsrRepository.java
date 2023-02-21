package dao;

import dto.UsrDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UsrRepository {
    public UsrDto getMemberByLoginId(String userId);
}
