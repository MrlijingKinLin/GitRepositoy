package com.taotao.redis;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientPool implements JedisClient {

	@Autowired
	JedisPool jedisPool;
	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public Long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long hset = jedis.hset(key, field, value);
		jedis.close();
		return hset;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		String hget = jedis.hget(key, field);
		jedis.close();
		return hget;
	}

	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Boolean exists = jedis.exists(key);
		jedis.close();
		return exists;
	}

	@Override
	public Long expire(String key, int seconds) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long expire = jedis.expire(key, seconds);
		jedis.close();
		return expire;
	}

	@Override
	public Long incr(String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long incr = jedis.incr(key);
		jedis.close();
		return incr;
	}

	@Override
	public Long hdel(String key, String... fields) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long hdel = jedis.hdel(key, fields);
		jedis.close();
		return hdel;
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}

}
