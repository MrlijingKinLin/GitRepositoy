import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
	/**
	 * 测试单机版的redis  跳过test install -DskipTests
	 */
	@Test
	public void TestJedis (){
		Jedis jedis = new Jedis("192.168.25.133",6379);
		jedis.set("user", "张三");
		String string = jedis.get("user");
		System.out.println(string);
		jedis.close();
	}
	@Test
	public void TestJedisPool(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setMaxTotal(20);
		JedisPool jedisPool = new JedisPool();
		Jedis jedis = jedisPool.getResource();
		jedis.set("username", "三哥");
		String string = jedis.get("username");
		System.out.println(string);
		jedis.close();
		jedisPool.close();
	}
	
	@Test
	public void TestjedisCluster(){
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.133", 7001));
		nodes.add(new HostAndPort("192.168.25.133", 7002));
		nodes.add(new HostAndPort("192.168.25.133", 7003));
		nodes.add(new HostAndPort("192.168.25.133", 7004));
		nodes.add(new HostAndPort("192.168.25.133", 7005));
		nodes.add(new HostAndPort("192.168.25.133", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("mytest", "测试类");
		String string = jedisCluster.get("mytest");
		System.out.println(string);
		jedisCluster.close();
	}
	
	@Test
	public void TestSpringJedisCluser(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		JedisCluster cluster = applicationContext.getBean(JedisCluster.class);
		cluster.set("mytest", "集群版测试结果");
		String string = cluster.get("mytest");
		System.out.println(string);
	}
	
	@Test
	public void TestSpringJedisPool(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		JedisPool jedisPool = applicationContext.getBean(JedisPool.class);
		Jedis jedis = jedisPool.getResource();
		jedis.set("mytest", "单机版测试");
		String string = jedis.get("mytest");
		System.out.println(string);
		jedis.close();
		
		
		
	}
	
}
