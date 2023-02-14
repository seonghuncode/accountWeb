package TestDao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestDao {

    @Select("SELECT * FROM test")
    public String showTest();

    @Select("SELECT SYSDATE FROM DUAL")
    public String getTime(); //getRime이 실행되면 위의 쿼리가 실행된다.
}
