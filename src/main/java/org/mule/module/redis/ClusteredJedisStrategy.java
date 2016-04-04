package org.mule.module.redis;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Transaction;

import javax.naming.OperationNotSupportedException;
import java.util.Set;

public class ClusteredJedisStrategy implements JedisStrategy {

    final JedisCluster cluster;

    public ClusteredJedisStrategy(JedisCluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public Long setnx(byte[] key, byte[] value) {
        return Long.parseLong(cluster.set(key, value));
    }

    @Override
    public Set<byte[]> hkeys(byte[] key) {
        return cluster.hkeys(key);
    }

    @Override
    public Set<byte[]> keys(byte[] key) {
        return cluster.hkeys(key);
    }

    @Override
    public Long del(byte[] key) {
        return cluster.del(key);
    }

    @Override
    public byte[] rpop(byte[] key) {
        return cluster.rpop(key);
    }

    @Override
    public Long lpushx(byte[] key, byte[] string) {
        return cluster.lpushx(key, string);
    }

    @Override
    public Long lpush(byte[] key, byte[] string) {
        return cluster.lpush(key, string);
    }

    @Override
    public Long sadd(byte[] key, byte[] string) {
        return cluster.sadd(key, string);
    }

    @Override
    public byte[] spop(byte[] key) {
        return cluster.spop(key);
    }

    @Override
    public byte[] srandmember(byte[] key) {
        return cluster.srandmember(key);
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member) {
        return cluster.zadd(key, score, member);
    }

    @Override
    public byte[] lpop(byte[] key) {
        return cluster.lpop(key);
    }

    @Override
    public Long rpushx(byte[] key, byte[] string) {
        return cluster.rpushx(key, string);
    }

    @Override
    public Long rpush(byte[] key, byte[] string) {
        return cluster.rpush(key, string);
    }

    @Override
    public Set<byte[]> zrange(byte[] key, long start, long end) {
        return cluster.zrange(key, start, end);
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        return cluster.zrangeByScore(key, min, max);
    }

    @Override
    public Set<byte[]> zrevrange(byte[] key, long start, long end) {
        return cluster.zrevrange(key, start, end);
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        return cluster.zrevrangeByScore(key,max,min);
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member) {
        return cluster.zincrby(key,score,member);
    }

    @Override
    public Long expireAt(byte[] key, long unixTime) {
        return cluster.expireAt(key,unixTime);
    }

    @Override
    public Long persist(byte[] key) {
        return cluster.persist(key);
    }

    @Override
    public Long ttl(byte[] key) {
        return cluster.ttl(key);
    }

    @Override
    public Long publish(byte[] channel, byte[] message) {
        return cluster.publish(channel, message);
    }

    @Override
    public void psubscribe(BinaryJedisPubSub jedisPubSub, byte[][] patterns) {
         cluster.psubscribe(jedisPubSub,patterns);
    }

    @Override
    public Boolean hexists(byte[] key, byte[] field) {
        return cluster.hexists(key,field);
    }

    @Override
    public Transaction multi() {
        // ToDo
        throw new UnsupportedOperationException ();
    }

    @Override
    public String set(byte[] key, byte[] value) {
        return cluster.set(key, value);
    }

    @Override
    public Long expire(byte[] key, int seconds) {
        return cluster.expire(key, seconds);
    }

    @Override
    public byte[] get(byte[] key) {
        return cluster.get(key);
    }

    @Override
    public Boolean exists(byte[] key) {
        return cluster.exists(key);
    }

    @Override
    public Long incr(byte[] key) {
        return cluster.incr(key);
    }

    @Override
    public Long incrBy(byte[] key, long integer) {
        return cluster.incrBy(key, integer);
    }

    @Override
    public Long decr(byte[] key) {
        return cluster.decr(key);
    }

    @Override
    public Long decrBy(byte[] key, long integer) {
        return cluster.decrBy(key, integer);
    }

    @Override
    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        return cluster.hsetnx(key, field, value);
    }

    @Override
    public Long hset(byte[] key, byte[] field, byte[] value) {
        return cluster.hset(key, field, value);
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        return cluster.hget(key, field);
    }

    @Override
    public Long hincrBy(byte[] key, byte[] field, long value) {
        return cluster.hincrBy(key, field, value);
    }


}
