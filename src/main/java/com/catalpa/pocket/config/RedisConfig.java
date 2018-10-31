package com.catalpa.pocket.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author bruce_wan
 * @since 2018/10/30
 */
@Log4j2
@Configuration
@EnableCaching
@EnableConfigurationProperties(value = {RedisProperties.class})
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 引入autoconfigure的RedisProperties类
     *
     * @param redisProperties
     * @return
     */
    @Bean
    public RedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {

        //在2.x中，需要通过RedisStandaloneConfiguration来设置服务器配置
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());

        //jedis连接池配置，配置方式和1.x相同
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        jedisPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());

        //在2.x中，需要通过JedisPoolingClientConfigurationBuilder来创建jedis连接池客户端配置
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder =
                JedisClientConfiguration.builder().usePooling();
        jedisPoolingClientConfigurationBuilder.poolConfig(jedisPoolConfig);
        JedisClientConfiguration jedisClientConfiguration = jedisPoolingClientConfigurationBuilder.build();

        //在2.x中创建JedisConnectionFactory,原有的一些set方法已经废弃
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    /**
     * 获取redisConnectionFactory的连接池
     *
     * @param redisConnectionFactory
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Bean
    public JedisPool jedisPool(RedisConnectionFactory redisConnectionFactory) throws NoSuchFieldException, IllegalAccessException {
        //通过反射的方式来获取连接池
        Field poolField = redisConnectionFactory.getClass().getDeclaredField("pool");
        poolField.setAccessible(true);
        return (JedisPool) poolField.get(redisConnectionFactory);
    }

    /**
     * @return 自定义策略生成的key
     * @description 自定义的缓存key的生成策略
     * 若想使用这个key  只需要讲注解上keyGenerator的值设置为keyGenerator即可</br>
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public RedisSerializer<Object> getRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean("springCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        // 将RedisCacheManager的序列化方式设置为Jackson2Json
        RedisSerializationContext.SerializationPair serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(getRedisSerializer());
        RedisCacheConfiguration configuration =
                RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().serializeValuesWith(serializationPair);

        // 设置TTL默认30秒
        configuration.entryTtl(Duration.ofSeconds(30));

        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();

        // 配置用户缓存
        RedisCacheConfiguration userCacheConfiguration = configuration.prefixKeysWith("user:").entryTtl(Duration.ofDays(7));
        redisCacheConfigurationMap.put("user:", userCacheConfiguration);

        // 配置打印机缓存
        RedisCacheConfiguration examCacheConfiguration = configuration.prefixKeysWith("printer:").entryTtl(Duration.ofDays(1));
        redisCacheConfigurationMap.put("printer:", examCacheConfiguration);

        // 配置系统缓存
        RedisCacheConfiguration systemCacheConfiguration = configuration.prefixKeysWith("system:").entryTtl(Duration.ZERO);
        redisCacheConfigurationMap.put("system:", systemCacheConfiguration);

        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        return new RedisCacheManager(redisCacheWriter, configuration, redisCacheConfigurationMap);
    }

    /**
     * RedisTemplate配置
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        //配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);//key序列化
        redisTemplate.setValueSerializer(getRedisSerializer());//value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
        redisTemplate.setHashValueSerializer(getRedisSerializer());//Hash value序列化
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}

