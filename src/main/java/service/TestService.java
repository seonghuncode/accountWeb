package service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TestRepository;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public String getList(){
        String result = testRepository.getList();
        return result;
    }


//    public List<TestVo> getList(TestVo testVo);




}
