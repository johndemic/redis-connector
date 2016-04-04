package org.mule.module.redis;


import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;

import java.util.Set;

public interface JedisStrategy {


    public Long setnx(byte[] key, byte[] value);

    public String set(byte[] key, byte[] value);

    public Long expire(byte[] key, final int seconds);

    public byte[] get(byte[] key);

    public Boolean exists(byte[] key);

    public Long incr(byte[] key);

    public Long incrBy(byte[] key, final long integer);

    public Long decr(byte[] key);

    public Long decrBy(byte[] key, final long integer);

    public Long hsetnx(byte[] key, byte[] field, byte[] value);

    public Long hset(byte[] key, byte[] field, byte[] value);

    public byte[] hget(byte[] key, byte[] field);

    public Long hincrBy(byte[] key, byte[] field, long value);

    public  Set<byte[]> hkeys(final byte[] key);

    public Set<byte[]> keys(final byte[] key);

    public Long del(byte[] key);

    public byte[] rpop(byte[] key);

    public Long lpushx(byte[] key, byte[] string);

    public Long lpush(byte[] key, byte[] string);

    public Long sadd(byte[] key, byte[] string);

    public byte[] spop(byte[] key);

    public byte[] srandmember(byte[] key);

    public Long zadd(byte[] key, final double score, byte[] member);

    public byte[] lpop(byte[] key);

    public Long rpushx(final byte[] key, final byte[] string);

    public Long rpush(final byte[] key, final byte[] string);

    public Set<byte[]> zrange(byte[] key, final long start, final long end);

    public Set<byte[]> zrangeByScore(byte[] key, final double min, final double max);

    public Set<byte[]> zrevrange(byte[] key, final long start, final long end);

    public Set<byte[]> zrevrangeByScore(byte[] key, final double max, final double min);

    public Double zincrby(byte[] key, final double score, byte[] member);

    public Long expireAt(byte[] key, final long unixTime);

    public Long persist(byte[] key);

    public Long ttl(byte[] key);

    public Long publish(byte[] channel, byte[] message);

    public void psubscribe(final BinaryJedisPubSub jedisPubSub, byte[][] patterns);

    public Boolean hexists(byte[]  key, byte[]  field);

    public Transaction multi();

}