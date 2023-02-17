package service;

import dao.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;




    public String getList(){
        String result = testRepository.getList();
        return result;
    }
}