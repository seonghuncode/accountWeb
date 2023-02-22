package service;


import dao.UsrRepository;
import dto.UsrDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsrServiceImpl implements UsrService {

    @Autowired
    private UsrRepository usrRepository;

    public UsrDto getMemberByLoginId(String userId) {
        UsrDto result = usrRepository.getMemberByLoginId(userId);
        return result;
    }





}
