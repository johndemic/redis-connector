/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.redis;

import java.io.ObjectStreamConstants;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.SafeEncoder;

public abstract class RedisUtils
{
    public static abstract class RedisAction<R>
    {
        protected volatile JedisStrategy redis;

        R runWithJedis(final JedisStrategy jedis)
        {
            redis = jedis;
            return run();
        }

        public abstract R run();
    }

    private static final Log LOGGER = LogFactory.getLog(RedisUtils.class);

    protected RedisUtils()
    {
        throw new UnsupportedOperationException("do not instantiate");
    }

    public static byte[] toBytes(final String string, final String encoding)
    {
        try
        {
            return string.getBytes(encoding);
        }
        catch (final UnsupportedEncodingException uee)
        {
            LOGGER.warn(String.format("Failed to get bytes from %s with encoding %s, using default encoding",
                string, encoding), uee);
            return string.getBytes();
        }
    }

    public static byte[] toBytes(final Serializable serializable)
    {
        if (serializable == null)
        {
            return null;
        }

        // preserve strings if possible
        if (serializable instanceof String)
        {
            return SafeEncoder.encode((String) serializable);
        }
        // serialize anything that isn't a string
        return SerializationUtils.serialize(serializable);
    }

    public static Serializable fromBytes(final byte[] bytes)
    {
        if ((bytes == null) || (bytes.length == 0))
        {
            return null;
        }

        if ((bytes[0] == (byte) ((ObjectStreamConstants.STREAM_MAGIC >>> 8) & 0xFF)))
        {
            final Object deserialized = SerializationUtils.deserialize(bytes);
            if (deserialized instanceof Serializable)
            {
                return (Serializable) deserialized;
            }
            else
            {
                return bytes;
            }
        }
        else
        {
            return SafeEncoder.encode(bytes);
        }
    }

    public static byte[] getPartitionHashKey(final String partitionName)
    {
        return SafeEncoder.encode(RedisConstants.OBJECTSTORE_HASH_KEY_PREFIX + partitionName);
    }

    public static byte[][] getPatternsFromChannels(final List<String> channels)
    {
        final byte[][] patterns = new byte[channels.size()][];
        for (int i = 0; i < channels.size(); i++)
        {
            patterns[i] = SafeEncoder.encode(channels.get(i));
        }
        return patterns;
    }

    public static <R> R run(final JedisStrategy jedis , final RedisAction<R> action)
    {
        return action.runWithJedis(jedis);

    }
}
