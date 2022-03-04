package study.fisco.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public void setKey(String key,String value){
        redisTemplate.opsForValue().set(key,value,3600,TimeUnit.SECONDS);
    }

    public String getKey(String key){
       return (String) redisTemplate.opsForValue().get(key);
    }
}
