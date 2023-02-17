package service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dao.TestRepository;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public String getList(){
        String result = testRepository.getList();
        return result;
    }




}
