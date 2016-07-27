/*
 * Copyright 2015 ireader.com All right reserved. This software is the
 * confidential and proprietary information of ireader.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package org.codepay.common.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.codepay.common.SeparatorConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Tuple;
import redis.clients.util.SafeEncoder;

/**
 * @author lisai
 * @Descriptions redis操作工具类.
 * @date 2015年3月30日
 */
public class JedisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisUtil.class);

    private static final Map<String, Redis> REDIS_POOL = new ConcurrentHashMap<String, JedisUtil.Redis>();

    private static final String URI_SEPARATOR = ":";

    /**
     * 可以从缓存池中分配对象的最大数量
     */
    private static int maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

    /**
     * 缓存池中最大空闲对象数量
     */
    private static int maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

    /**
     * 缓存池中最小空闲对象数量
     */
    private static int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

    /**
     * 逐出连接的最大空闲时间
     */
    private static long maxWaitMillis = GenericObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS;

    /**
     * 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法
     */
    private static boolean testOnBorrow = GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW;

    /**
     * 初始化参数.
     *
     * @param maxTotal      可以从缓存池中分配对象的最大数量
     * @param minIdle       缓存池中最小空闲对象数量
     * @param maxIdle       缓存池中最大空闲对象数量
     * @param maxWaitMillis 逐出连接的最大空闲时间
     * @param testOnBorrow  获取连接时是否检测可用
     */
    public static void initialize(int maxTotal, int minIdle, int maxIdle, long maxWaitMillis, boolean testOnBorrow) {
        JedisUtil.minIdle = minIdle;
        JedisUtil.maxIdle = maxIdle;
        JedisUtil.maxTotal = maxTotal;
        JedisUtil.maxWaitMillis = maxWaitMillis;
        JedisUtil.testOnBorrow = testOnBorrow;
    }

    /**
     * 获取redis连接.
     *
     * @param uri 连接地址(①host:port ②host:port:password)
     * @return
     */
    public static Redis get(String uri) {
        if (StringUtils.isBlank(uri)) {
            return null;
        }
        Redis redis = REDIS_POOL.get(uri);
        if (null == redis) {
            String[] config = uri.split(URI_SEPARATOR);
            if (2 == config.length) {
                redis = new Redis(config[0], Integer.parseInt(config[1]));
                REDIS_POOL.put(uri, redis);
            }
            if (3 == config.length) {
                redis = new Redis(config[0], Integer.parseInt(config[1]), config[3]);
                REDIS_POOL.put(uri, redis);
            }
        }
        return redis;
    }

    public static class Redis {

        private JedisPool jedisPool;

        public Redis(String host, int port) {
            JedisPoolConfig jedisPoolConfig = initPoolConfig();
            // 构造连接池
            this.jedisPool = new JedisPool(jedisPoolConfig, host, port, Protocol.DEFAULT_TIMEOUT);
        }

        public Redis(String host, int port, String password) {
            JedisPoolConfig jedisPoolConfig = initPoolConfig();
            // 构造连接池
            this.jedisPool = new JedisPool(jedisPoolConfig, host, port, Protocol.DEFAULT_TIMEOUT, password);
        }

        public void close() {
            jedisPool.close();
            jedisPool = null;
        }

        private JedisPoolConfig initPoolConfig() {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(JedisUtil.maxTotal);
            config.setMinIdle(JedisUtil.minIdle);
            config.setMaxIdle(JedisUtil.maxIdle);
            config.setMaxWaitMillis(JedisUtil.maxWaitMillis);
            config.setTestOnBorrow(JedisUtil.testOnBorrow);
            config.setTestOnReturn(true);
            return config;
        }

        /**
         * 从池里获取一个jedis.
         *
         * @return
         */
        private Jedis getJedis() {
            Jedis jedis = null;
            for (int i = 0; i < 5; i++) {
                try {
                    jedis = jedisPool.getResource();
                    break;
                } catch (Exception e) {
                    LOGGER.error("get redis master failed!,error message :{}", e.getMessage());
                    jedisPool.returnBrokenResource(jedis);
                }
            }
            return jedis;
        }

        /**
         * setex 设置值 并指定键值对应的有效期 单位为秒<br>
         * 如果key 存在 覆盖旧值 <br>
         */
        public void setex(String key, int seconds, String value) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                jedis.setex(key, seconds, value);
                // 还会到连接池
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 如果存在相同的key 覆盖旧值<br>
         */
        public boolean set(String key, String value) {
            // 从池中获取一个jedis实例
            Jedis jedis = getJedis();
            try {
                String stateCode = jedis.set(key, value);
                return "OK".equals(stateCode);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 如果存在相同的key 覆盖旧值<br>
         */
        public boolean set(String key, byte[] value) {
            // 从池中获取一个jedis实例
            Jedis jedis = getJedis();
            try {
                String stateCode = jedis.set(SafeEncoder.encode(key), value);
                return "OK".equals(stateCode);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * setnx 如果key存在 不做任何操作 返回0, <br>
         * 如果key不存在 设置值成功 返回1 <br>
         */
        public Long setnx(String key, String value) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.setnx(key, value);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }

        }

        /**
         * setnx 如果key存在 不做任何操作 返回false, <br>
         * 缓存时间以毫秒为单位 <br>
         */
        public boolean setnxpx(String key, String value, long ttl) {
            // 从池中获取一个jedis实例
            Jedis jedis = getJedis();
            try {
                String stateCode = jedis.set(key, value, "NX", "PX", ttl);
                return "OK".equals(stateCode);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * setex 如果key存在 覆盖旧值，原有ttl覆盖为新ttl<br>
         * 缓存时间以毫秒为单位 <br>
         */
        public boolean setxxpx(String key, String value, long ttl) {
            // 从池中获取一个jedis实例
            Jedis jedis = getJedis();
            try {
                String stateCode = jedis.set(key, value, "XX", "PX", ttl);
                return "OK".equals(stateCode);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * setrange 通过key 和 offset 替换value <br>
         * 例如:setex - > setex_value jedis.setrange("setex", 6, "Setrange") <br>
         * 替换为 setex_Setrange <br>
         */
        public void setrange(String key, int offset, String value) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                jedis.setrange(key, offset, value);
                // 还会到连接池
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * mset 同时设置一个或多个 key-value对。 如果某个key-value 存在 会用新值覆盖原来的旧值, 总是成功， 成功返回OK <br>
         */
        public void mset(String... keysvalues) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                jedis.mset(keysvalues);
                // 还会到连接池
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * msetnx 同时设置一个或多个 key-value对。 如果某个key-value存在 返回0 所有操作都会回滚, 如果成功 返回ok <br>
         */
        public void msetnx(String... keysvalues) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                jedis.msetnx(keysvalues);
                // 还会到连接池
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * get 通过key 获取对应的value 如果key不存在 返回nil <br>
         */
        public String get(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.get(key);
                // 还会到连接池
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 获取key生存时间，以秒为单位<br>
         */
        public Long ttl(String key) {
            Jedis jedis = null;
            try {
                // 从池中获取一个jedis实例
                jedis = getJedis();
                return jedis.ttl(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * get 通过key 获取对应的value 如果key不存在 返回nil <br>
         */
        public byte[] getBinary(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.get(SafeEncoder.encode(key));
            } finally {
                // 还回到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        public <T extends Serializable> T getBean(String key) throws IOException, ClassNotFoundException {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                byte[] bytes = jedis.get(SafeEncoder.encode(key));
                if(null == bytes || bytes.length <=0 ){
                    return null;
                }
                return SerializerUtil.deserialize(bytes);
            } finally {
                // 还回到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        public boolean setExBean(String key, int seconds, Serializable bean) throws IOException {
            // 从池中获取一个jedis实例
            Jedis jedis = getJedis();
            try {
                byte[] bytes = SerializerUtil.serialize(bean);
                String stateCode = jedis.set(SafeEncoder.encode(key), bytes);
                return "OK".equals(stateCode);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * getset 通过key 获取对应的value 然后通过key 设置新的value <br>
         */
        public String getSet(String key, String value) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.getSet(key, value);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 返回key对应的value 在由start 和 end 两个偏移量截取 <br>
         */
        public String getrange(String key, int startOffset, int endOffset) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.getrange(key, startOffset, endOffset);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 返回多个key 对应的value <br>
         */
        public List<String> mget(String... keys) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.mget(keys);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 对key对应的value 做+1操作 返回+1后的新值 <br>
         */
        public Long incr(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.incr(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 对key对应的value 加指定值 返回新值 如果key不存在 认为原来的value为0 <br>
         */
        public Long incrBy(String key, long integer) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.incrBy(key, integer);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 对key对应的value 做-1操作 返回新值 <br>
         */
        public Long decr(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.decr(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 对key对应的value 减去指定值 返回新值 如果key不存在 认为原来的value为0 <br>
         */
        public Long decrBy(String key, int integer) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.decrBy(key, integer);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 给指定的key的值追加, 返回新字符串的长度 <br>
         */
        public Long append(String key, String value) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.append(key, value);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 取得指定key的value值的长度 <br>
         */
        public Long strlen(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.strlen(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 删除指定key<br>
         */
        public Long del(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.del(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 右侧入列 <br>
         */
        public Long rpush(String key, String... strings) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.rpush(key, strings);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 左侧出列<br>
         */
        public String lpop(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.lpop(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 队列长度
         */
        public Long llen(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.llen(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 返回列表 key 中指定区间内的元素<br>
         * 区间以偏移量 start 和 stop 指定
         */
        public List<String> lrange(String key, long start, long end) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.lrange(key, start, end);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 返回列表 key 中指定区间内的元素<br>
         * 区间以偏移量 start 和 stop 指定
         */
        public boolean ltrim(String key, long start, long end) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                String stateCode = jedis.ltrim(key, start, end);
                return "OK".equals(stateCode);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 检测是否存在<br>
         */
        public boolean exists(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.exists(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 增加<br>
         */
        public Long sadd(String key, String... members) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.sadd(key, members);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 增加<br>
         */
        public Long smove(String srckey, String dstkey, String member) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.smove(srckey, dstkey, member);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 是否存在 类似 list contains<br>
         */
        public Boolean sismember(String key, String member) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.sismember(key, member);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 随机获取一个值.
         *
         * @param key
         * @return
         */
        public String srandmember(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.srandmember(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 长度<br>
         */
        public Long scard(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.scard(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 所有值<br>
         */
        public Set<String> smembers(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.smembers(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * set 删除<br>
         */
        public Long srem(String key, String... members) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.srem(key, members);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * map 增加<br>
         */
        public String hmset(String key, Map<String, String> hash) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.hmset(key, hash);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 取出map中的字段值<br>
         */
        public List<String> hmset(String key, String... fields) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.hmget(key, fields);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 删除map中的某一个键值<br>
         */
        public Long hdel(String key, String... fields) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.hdel(key, fields);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * map中的所有键值<br>
         */
        public Set<String> hkeys(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.hkeys(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * map中的所有value<br>
         */
        public List<String> hvals(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.hvals(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * map中key
         *
         * @param key
         * @param field map的key
         * @return
         */
        public String hget(String key, String field) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.hget(key, field);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 添加memeber到key对应的set中<br>
         */
        public Long zadd(String key, double score, String member) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.zadd(key, score, member);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 删除sortedSet中指定区间的数据,start和end代表score
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Long zremRangeByScore(String key, double start, double end) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.zremrangeByScore(key, start, end);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 获取指定区间的数据
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Set<String> zrange(String key, long start, long end) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.zrange(key, start, end);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 设置过期时间.
         *
         * @param key
         * @param seconds 秒
         * @return
         */
        public void expire(String key, int seconds) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                jedis.expire(key, seconds);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 获取指定区间的数据，并且包含分数
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Set<Tuple> zrangeWithScore(String key, long start, long end) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.zrangeWithScores(key, start, end);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 删除指定元素<br>
         */
        public Long lrem(String key, long count, String value) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.lrem(key, count, value);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 右侧出列<br>
         */
        public String rpop(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.rpop(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 左侧入列 <br>
         */
        public Long lpush(String key, String... strings) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.lpush(key, strings);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 集合出列<br>
         */
        public String spop(String key) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.spop(key);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 发布消息<br>
         */
        public Long publish(String channel, String message) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.publish(channel, message);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        /**
         * 订阅消息<br>
         */
        public void subscribe(JedisPubSub subscriber, String channel) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                jedis.subscribe(subscriber, channel);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }

        public String rpoplpush(String srckey, String dstkey) {
            // 从池中获取一个jedis实例
            Jedis jedis = null;
            try {
                jedis = getJedis();
                return jedis.rpoplpush(srckey, dstkey);
            } finally {
                // 还会到连接池
                if (null != jedis) {
                    jedis.close();
                }
            }
        }
    }

    static {
        Thread cleaner = new Thread(new Cleaner());
        cleaner.setDaemon(true);
        cleaner.start();
    }

    private static class Cleaner implements Runnable {

        @Override
        public void run() {
            List<String> errors = new ArrayList<String>();
            while (true) {
                try {
                    Thread.sleep(60000);
                    if (REDIS_POOL.size() <= 0) {
                        continue;
                    }
                    for (Map.Entry<String, Redis> entry : REDIS_POOL.entrySet()) {
                        try {
                            Redis redis = entry.getValue();
                            if (null == redis) {
                                errors.add(entry.getKey());
                            }
                            redis.get("redis");
                        } catch (Exception e) {
                            errors.add(entry.getKey());
                            LOGGER.warn("redis connection failure!!uri:{}", entry.getKey());
                        }
                    }

                    if (errors.isEmpty()) {
                        continue;
                    }

                    for (String uri : errors) {
                        REDIS_POOL.remove(uri);
                    }
                    errors.clear();
                } catch (Exception e) {
                    LOGGER.error("redis daemon thread exception!!!message:" + e.getMessage(), e);
                }
            }
        }

    }

    /**
     * 生成缓存key.
     *
     * @param array 参数
     * @return
     */
    public static String createCacheKey(Object... array) {
        return StringUtils.join(array, SeparatorConstant.UNDERSCODE);
    }
}
