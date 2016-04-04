package org.mule.module.redis;

import redis.clients.jedis.*;

import java.util.Set;

public class PooledJedisStrategy implements JedisStrategy {

    final JedisPool jedisPool;

    public PooledJedisStrategy(JedisPool jedisPool) {
        this.jedisPool = jedisPool;

    }

    @Override
    public Long setnx(byte[] key, byte[] value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.setnx(key, value);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public String set(byte[] key, byte[] value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long expire(byte[] key, int seconds) {

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.expire(key, seconds);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public byte[] get(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Boolean exists(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long incr(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long incrBy(byte[] key, long integer) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.incrBy(key, integer);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long decr(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long decrBy(byte[] key, long integer) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.decrBy(key, integer);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.hsetnx(key, field, value);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long hset(byte[] key, byte[] field, byte[] value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key, field, value);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long hincrBy(byte[] key, byte[] field, long value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(key, field, value);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Set<byte[]> hkeys(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.keys(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Set<byte[]> keys(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.keys(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long del(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public byte[] rpop(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.rpop(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long lpushx(byte[] key, byte[] string) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.lpushx(key, string);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long lpush(byte[] key, byte[] string) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, string);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long sadd(byte[] key, byte[] string) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, string);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public byte[] spop(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.spop(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public byte[] srandmember(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.srandmember(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(key, score, member);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public byte[] lpop(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long rpushx(byte[] key, byte[] string) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.rpushx(key, string);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long rpush(byte[] key, byte[] string) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key, string);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Set<byte[]> zrange(byte[] key, long start, long end) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.zrange(key, start, end);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Set<byte[]> zrevrange(byte[] key, long start, long end) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key, start, end);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByScore(key, max, min);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.zincrby(key, score, member);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long expireAt(byte[] key, long unixTime) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.expireAt(key, unixTime);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long persist(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.persist(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long ttl(byte[] key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Long publish(byte[] channel, byte[] message) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.publish(channel, message);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public void psubscribe(BinaryJedisPubSub jedisPubSub, byte[][] patterns) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
             jedis.psubscribe(jedisPubSub, patterns);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Boolean hexists(byte[] key, byte[] field) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(key, field);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    @Override
    public Transaction multi() {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            return jedis.multi();
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

}
