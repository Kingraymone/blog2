package top.king.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;


public class RedisUtil implements InitializingBean {
    // namespace
    public final static String SYS_MENU = "blog:menu";

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean set(String key, String val) {
        try {
            redisTemplate.opsForValue().set(key, val);
            return true;
        } catch (Exception e) {
            logger.debug("redis set error",e);
            return false;
        }
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始完成后处理
    }
}
