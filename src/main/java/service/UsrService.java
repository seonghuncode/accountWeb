package service;

import dto.UsrDto;
import org.springframework.stereotype.Service;

@Service
public interface UsrService {
    public UsrDto getMemberByLoginId(String userId);


}
