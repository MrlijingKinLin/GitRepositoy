package com.taotao.redis;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient {
	@Autowired
	JedisCluster jedisCluster;
	
	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		String string = jedisCluster.set(key, value);
		return string;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		String string = jedisCluster.get(key);
		return string;
	}

	@Override
	public Long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		Long hset = jedisCluster.hset(key, field, value);
		return hset;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		String hget = jedisCluster.hget(key, field);
		return hget;
	}

	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		Boolean exists = jedisCluster.exists(key);
		return exists;
	}

	@Override
	public Long expire(String key, int seconds) {
		// TODO Auto-generated method stub
		Long expire = jedisCluster.expire(key, seconds);
		return expire;
	}

	@Override
	public Long incr(String key) {
		// TODO Auto-generated method stub
		Long incr = jedisCluster.incr(key);
		return incr;
	}

	@Override
	public Long hdel(String key, String... fields) {
		// TODO Auto-generated method stub
		Long hdel = jedisCluster.hdel(key, fields);
		return hdel;
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		Long del = jedisCluster.del(key);
		return del;
	}

}
