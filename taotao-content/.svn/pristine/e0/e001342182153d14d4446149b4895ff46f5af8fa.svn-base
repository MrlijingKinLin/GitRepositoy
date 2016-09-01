import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.jedis.JedisClient;

public class TestJedis {
	@Test
	public void testJedisPoolClient(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.hset("test", "key1", "三哥的");
		String hget = jedisClient.hget("test", "key1");
		System.out.println(hget);
	}
}
