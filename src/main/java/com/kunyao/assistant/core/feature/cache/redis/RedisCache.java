package com.kunyao.assistant.core.feature.cache.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * RedisCache : redis 缓存 插件
 * 
 * 可优化（将IP及端口放在配置文件中维护，不用修改源代码）
 *
 * @author GeNing
 * @since 2016.04.16
 */
@Component(value = "redisCache")
public class RedisCache {
	
    private int port = 6379;
    private String host = "127.0.0.1";
    private Jedis jedis = new Jedis(host, port);

    public String cache(String key, String value, int seconds) {
        String result = jedis.set(key, value);
        jedis.expire(key, seconds);
        return result;
    }

    public String get(String key) {
        return jedis.get(key);
    }
    
    public Jedis getJedis() {
    	return jedis;
    }
}
