//package service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import repository.TestRepository;
//import vo.TestVo;
//
//import java.util.List;
//
//@Service
//public class TestServiceImpl implements TestService {
//
//    @Autowired
//    private TestRepository testRepository;
//
//    @Override
//    public List<TestVo> getList(TestVo testVo){
//        List<TestVo> result = testRepository.getList(testVo);
//        return result;
//    }
//}
