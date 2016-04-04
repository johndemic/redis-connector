/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.MuleMessageCollection;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;
import org.mule.util.MapUtils;
import org.mule.util.UUID;

public class RedisDataStructureITCase extends FunctionalTestCase
{
    private static final String SIDE_PROP = "side";
    private static final String FIELD_PROP = "field";
    private static final String KEY_PROP = "key";
    private static final String TEST_KEY_PREFIX = "mule.tests.";
    private MuleClient muleClient;

    @Override
    protected String getConfigResources() {
        return "redis-datastructures-tests-config.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        muleClient = new MuleClient(muleContext);
    }

    @Test
    public void testStrings() throws Exception {
        final String testPayload = RandomStringUtils.randomAlphanumeric(20);
        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();

        assertFalse(muleClient.send("vm://key-existence.in", "ignored",
                Collections.singletonMap(KEY_PROP, (Object) testKey), 1).getPayload(Boolean.class));

        final MuleMessageCollection writerResults = (MuleMessageCollection) muleClient.send(
                "vm://strings-writer.in", testPayload, Collections.singletonMap(KEY_PROP, (Object) testKey));

        assertEquals(6, writerResults.size());
        assertTrue(writerResults.getMessage(0).getPayload() instanceof byte[]);
        assertEquals(testPayload, writerResults.getMessage(0).getPayloadAsString());
        assertTrue(writerResults.getMessage(1).getPayload() instanceof byte[]);
        assertEquals(testPayload, writerResults.getMessage(1).getPayloadAsString());
        assertTrue(writerResults.getMessage(2).getPayload() instanceof byte[]);
        assertEquals(testPayload, writerResults.getMessage(2).getPayloadAsString());
        assertEquals(NullPayload.getInstance(), writerResults.getMessage(4).getPayload());
        assertTrue(writerResults.getMessage(5).getPayload() instanceof byte[]);
        assertEquals(testKey, writerResults.getMessage(5).getPayloadAsString());

        // wait a little more than the TTL of 1 second
        Thread.sleep(2000L);

        assertTrue(muleClient.send("vm://key-existence.in", "ignored",
                Collections.singletonMap(KEY_PROP, (Object) testKey)).getPayload(Boolean.class));
        assertEquals(testPayload,
                muleClient.send("vm://strings-reader.in", "ignored", Collections.singletonMap(KEY_PROP, (Object) testKey))
                        .getPayloadAsString());
        assertEquals(
                NullPayload.getInstance(),
                muleClient.send("vm://strings-reader.in", "ignored",
                        Collections.singletonMap(KEY_PROP, (Object) new StringBuilder().append(testKey).append(".ttl")))
                        .getPayload());
        assertEquals(
                testPayload,
                muleClient.send("vm://strings-reader.in", "ignored",
                        Collections.singletonMap(KEY_PROP,
                                (Object) new StringBuilder().append(testKey).append(".other"))).getPayloadAsString());
        assertEquals(
                testKey,
                muleClient.send("vm://strings-reader.in", "ignored",
                        Collections.singletonMap(KEY_PROP,
                                (Object) new StringBuilder().append(testKey).append(".value"))).getPayloadAsString());
    }

    @Test
    public void testIncrementDecrement() throws Exception {
        final String testPayload = RandomStringUtils.randomAlphanumeric(20);
        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();
        final MuleMessage response = muleClient.send("vm://incr-decr.in", testPayload,
                Collections.singletonMap(KEY_PROP, (Object) testKey));

        assertEquals(-2L, response.getPayload());
    }

    @Test
    public void testHashes() throws Exception {
        final String testPayload = RandomStringUtils.randomAlphanumeric(20);
        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();
        final String testField = UUID.getUUID();

        final Map<String, Object> props = new HashMap<String, Object>();
        props.put(KEY_PROP, testKey);
        props.put(FIELD_PROP, testField);

        muleClient.send("vm://hashes-writer.in", testPayload, props);

        assertEquals(testPayload, muleClient.send("vm://hashes-reader.in", "ignored", props)
                .getPayloadAsString());
        props.put(FIELD_PROP, testField + ".other");
        assertEquals(testPayload, muleClient.send("vm://hashes-reader.in", "ignored", props)
                .getPayloadAsString());

        props.put(KEY_PROP, testKey + ".value");
        props.put(FIELD_PROP, testField);
        assertEquals(testKey, muleClient.send("vm://hashes-reader.in", "ignored", props).getPayloadAsString());
    }

    @Test
    public void testHashIncrement() throws Exception {
        final String testPayload = RandomStringUtils.randomAlphanumeric(20);
        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();
        final String testField = UUID.getUUID();

        final Map<String, Object> props = new HashMap<String, Object>();
        props.put(KEY_PROP, testKey);
        props.put(FIELD_PROP, testField);

        final MuleMessage response = muleClient.send("vm://hashes-incr.in", testPayload, props);

        assertEquals(-4L, response.getPayload());
    }

    @Test
    public void testLists() throws Exception {
        final String testPayload = RandomStringUtils.randomAlphanumeric(20);
        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();
        muleClient.send("vm://lists-writer.in", testPayload, Collections.singletonMap(KEY_PROP, (Object) testKey));

        final Map<String, Object> props = new HashMap<String, Object>();
        props.put(KEY_PROP, testKey);
        props.put(SIDE_PROP, "LEFT");
        assertEquals(testPayload, muleClient.send("vm://lists-reader.in", "ignored", props)
                .getPayloadAsString());
        props.put(SIDE_PROP, "RIGHT");
        assertEquals(testPayload, muleClient.send("vm://lists-reader.in", "ignored", props)
                .getPayloadAsString());

        props.put(KEY_PROP, testKey + ".value");
        assertEquals(testKey, muleClient.send("vm://lists-reader.in", "ignored", props).getPayloadAsString());
    }

    @Test
    public void testSets() throws Exception {
        final String testPayload = RandomStringUtils.randomAlphanumeric(20);
        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();

        final MuleMessageCollection writerResults = (MuleMessageCollection) muleClient.send(
                "vm://sets-writer.in", testPayload, Collections.singletonMap(KEY_PROP, (Object) testKey));
        assertEquals(4, writerResults.size());
        assertEquals(testPayload, writerResults.getMessage(0).getPayloadAsString());
        assertEquals(NullPayload.getInstance(), writerResults.getMessage(1).getPayload());
        assertEquals(testPayload, writerResults.getMessage(2).getPayloadAsString());
        assertEquals(testKey, writerResults.getMessage(3).getPayloadAsString());

        final MuleMessageCollection readerResults = (MuleMessageCollection) muleClient.send(
                "vm://sets-reader.in", "ignored", Collections.singletonMap(KEY_PROP, (Object) testKey));
        assertEquals(4, readerResults.size());
        assertEquals(testPayload, readerResults.getMessage(0).getPayloadAsString());
        assertEquals(testPayload, readerResults.getMessage(1).getPayloadAsString());
        assertEquals(NullPayload.getInstance(), readerResults.getMessage(2).getPayload());
        assertEquals(testKey, readerResults.getMessage(3).getPayloadAsString());
    }

    @Test
    public void testSortedSets() throws Exception {
        String testPayload = RandomStringUtils.randomAlphanumeric(20);
        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();

        final Map<String, Object> props = new HashMap<String, Object>();
        props.put(KEY_PROP, testKey);
        props.put("score", "1");
        MuleMessageCollection writerResults = (MuleMessageCollection) muleClient.send(
                "vm://sorted-sets-writer.in", testPayload, props);
        assertEquals(4, writerResults.size());
        assertEquals(testPayload, writerResults.getMessage(0).getPayloadAsString());
        assertEquals(NullPayload.getInstance(), writerResults.getMessage(1).getPayload());
        assertEquals(testPayload, writerResults.getMessage(2).getPayloadAsString());
        assertEquals(testKey, writerResults.getMessage(3).getPayloadAsString());

        testPayload = RandomStringUtils.randomAlphanumeric(20);
        props.put("score", "2.5");
        writerResults = (MuleMessageCollection) muleClient.send("vm://sorted-sets-writer.in", testPayload,
                props);
        assertEquals(4, writerResults.size());
        assertEquals(testPayload, writerResults.getMessage(0).getPayloadAsString());
        assertEquals(NullPayload.getInstance(), writerResults.getMessage(1).getPayload());
        assertEquals(testPayload, writerResults.getMessage(2).getPayloadAsString());
        assertEquals(testKey, writerResults.getMessage(3).getPayloadAsString());

        final MuleMessageCollection readerResults = (MuleMessageCollection) muleClient.send(
                "vm://sorted-sets-reader.in", "ignored", Collections.singletonMap(KEY_PROP, (Object) testKey));
        assertEquals(4, readerResults.size());
        for (int i = 0; i < 4; i++) {
            final Object payload = readerResults.getMessage(i).getPayload();
            assertTrue(testPayload, payload instanceof Set<?>);
            assertEquals("size of " + i, 2, ((Set<?>) readerResults.getMessage(i).getPayload()).size());
        }
    }

    @Test
    public void testSortedSetIncrement() throws Exception {
        final String testPayload = RandomStringUtils.randomAlphanumeric(20);
        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();

        final MuleMessageCollection writerResults = (MuleMessageCollection) muleClient.send(
                "vm://sorted-set-incr.in", testPayload, Collections.singletonMap(KEY_PROP, (Object) testKey));

        assertEquals(2, writerResults.size());
        assertEquals(3.14, writerResults.getMessage(0).getPayload());
        assertEquals(2.72, writerResults.getMessage(1).getPayload());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testKeyVolatility() throws Exception {
//        final String testKey = TEST_KEY_PREFIX + UUID.getUUID();
//        final Long expireAtUnixTime = System.currentTimeMillis() / 1000L + 20L;
//        final Map<String, Object> properties = MapUtils.mapWithKeysAndValues(HashMap.class,
//            Arrays.asList(KEY_PROP, "expireAtUnixTime"), Arrays.asList(testKey, expireAtUnixTime));
//        final MuleMessageCollection keyVolatilityResults = (MuleMessageCollection) muleClient.send(
//            "vm://key-volatility.in", "ignored", properties);
//        assertEquals(5, keyVolatilityResults.size());
//
//        final Object[] expectedResults = {true, true, 20L, true, -1L};
//
//        for (int i = 0; i < 5; i++)
//        {
//            assertEquals(expectedResults[i], keyVolatilityResults.getMessage(i).getPayload());
//        }
    }
}
