import cn.edu.bjut.reggie.ReggieApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = ReggieApplication.class)
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){
        redisTemplate.opsForValue().set("name", "zhangsan");
        String city = (String) redisTemplate.opsForValue().get("name");
        System.out.println(city);
    }
}
