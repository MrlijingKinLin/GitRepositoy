package com.taotao.jedis;

public interface JedisClient {
	String set(String key,String value);
	String get(String key);
	Long del(String key);
	Long hset(String key,String field,String value);
	String hget(String key,String field);
	Boolean exists(String key);
	Long expire(String key,int seconds);
	Long incr(String key);
	Long hdel(String key,String ... field);
}
