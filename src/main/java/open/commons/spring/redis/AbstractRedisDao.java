/*
 * Copyright 2020 Park Jun-Hong (parkjunhong77@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *
 * This file is generated under this project, "open-commons-spring-redis".
 *
 * Date  : 2020. 12. 1. 오후 4:38:45
 *
 * Author: Park_Jun_Hong_(parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import open.commons.core.TwoValueObject;
import open.commons.core.collection.FIFOMap;

/**
 * Redis Dao 클래스.
 * 
 * @since 2020. 12. 1.
 * @version 0.1.0
 * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
 */
public abstract class AbstractRedisDao<K, V, F extends RedisConnectionFactory> implements InitializingBean, DisposableBean {

    /** 두 키를 비교해서 더 큰 값을 반환한다. */
    protected static final BiFunction<String, String, String> MAX_KEY_COMPARATOR = (k1, k2) -> {
        if (k1 != null && k2 != null) {
            return k1.compareTo(k2) > -1 ? k1 : k2;
        } else if (k1 != null) {
            return k1;
        } else if (k2 != null) {
            return k2;
        } else {
            return null;
        }
    };

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /** 기본 Redis 연동 객체 */
    protected RedisTemplate<K, V> redisTemplate;

    /**
     * 
     * @since 2020. 12. 1.
     */
    public AbstractRedisDao() {
    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 12. 1.		parkjunhong77@gmail.com			최초 작성
     * </pre>
     *
     * @throws Exception
     *
     * @since 2020. 12. 1.
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**
     * {@link RedisConnectionFactory} 를 이용하여 ${@link RedisTemplate} 객체를 생성한다. <br>
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 12. 1.		parkjunhong77@gmail.com			최초 작성
     * </pre>
     *
     * @param factory
     * @return
     *
     * @since 2020. 12. 1.
     * @version _._._
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     */
    public RedisTemplate<K, V> createRedisTemplate(F factory) {

        RedisTemplate<K, V> tpl = new RedisTemplate<>();

        tpl.setKeySerializer(keySerializer());
        tpl.setValueSerializer(valueSerializer());
        tpl.setStringSerializer(stringSerializer());
        tpl.setConnectionFactory(factory);

        return tpl;

    }

    /**
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 12. 1.		parkjunhong77@gmail.com			최초 작성
     * </pre>
     *
     * @throws Exception
     *
     * @since 2020. 12. 1.
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     *
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
    }

    /**
     * 접속하려는 database 번호를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 12. 1.      parkjunhong77@gmail.com         최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 12. 1.
     * 
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     */
    protected abstract int getDatabase();

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 12. 1.      parkjunhong77@gmail.com         최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 12. 1.
     * 
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     */
    protected abstract F getRedisConnectionFactory();

    /**
     * database 키를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 12. 1.      parkjunhong77@gmail.com         최초 작성
     * </pre>
     * 
     * @param pattern
     *            키 패턴.
     * @param partition
     *            키를 찾기 위해서 읽어오는 사이즈
     * @param comparator
     *            비교 연산
     * @return 키값. (nullable)
     *
     * @since 2020. 12. 1.
     * 
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     */
    @SuppressWarnings("unchecked")
    protected K key(String pattern, int partition, BiFunction<K, K, K> comparator) {

        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(100).build();
        RedisConnectionFactory con = getRedisConnectionFactory();

        K key = null;
        Cursor<byte[]> cursor = con.getConnection().scan(options);

        RedisSerializer<K> keySerializer = (RedisSerializer<K>) redisTemplate.getKeySerializer();
        while (cursor.hasNext()) {
            if (key == null) {
                key = keySerializer.deserialize(cursor.next());
                continue;
            }
            key = comparator.apply(key, keySerializer.deserialize(cursor.next()));
        }

        return key;
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 4. 8.      parkjunhong77@gmail.com         최초 작성
     * </pre>
     *
     * @param <K>
     * @param pattern
     * @param partition
     * @return
     * 
     * @since 2020. 4. 8.
     * 
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     */
    @SuppressWarnings("unchecked")
    protected Collection<K> keys(String pattern, int partition) {

        Set<K> keys = new HashSet<>();

        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(partition).build();
        RedisConnectionFactory con = getRedisConnectionFactory();

        Cursor<byte[]> cursor = con.getConnection().scan(options);

        RedisSerializer<K> keySerializer = (RedisSerializer<K>) this.redisTemplate.getKeySerializer();
        while (cursor.hasNext()) {
            keys.add(keySerializer.deserialize(cursor.next()));
        }

        return keys;
    }

    /**
     * '키' 데이터 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 12. 1.		parkjunhong77@gmail.com			최초 작성
     * </pre>
     *
     * @return
     *
     * @since 2020. 12. 1.
     * @version _._._
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     */
    protected abstract RedisSerializer<?> keySerializer();

    /**
     * 대량의 데이터를 분할 조회하여 제공한다. <br>
     * Redis 는 single thread 이기 때문에, 대량의 데이터를 조회하는 경우 성능에 영향을 줄 수 있기에.
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 12. 1.      parkjunhong77@gmail.com         최초 작성
     * </pre>
     *
     * @param op
     *            Redis 명령어
     * @param key
     *            키값
     * @param workerCount
     *            동시에 작업할 개수
     * @param partitionSize
     *            한번에 읽어올 데이터 크기
     * @param filterIn
     *            매칭 조건
     * @return
     *
     * @since 2020. 12. 1.
     * 
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     */
    protected List<V> lrange(ListOperations<K, V> op, K key, int workerCount, long partitionSize, Predicate<V> filterIn) {

        List<V> total = new Vector<>();

        long start = 0;
        long end = partitionSize;

        // List<V> partition = null;
        List<TwoValueObject<Long, Long>> ranges = new ArrayList<>();
        final AtomicInteger comeback = new AtomicInteger(0);
        while (true) {
            for (int i = 0; i < Math.max(1, workerCount); i++) {
                ranges.add(new TwoValueObject<Long, Long>(start, end));
                start += partitionSize;
                end += partitionSize;
            }

            ranges.parallelStream() //
                    // Redis 조회
                    .map(range -> op.range(key, range.first, range.second)) //
                    // 데이터 필터링
                    .filter(part -> part != null && part.size() > 0) //
                    .forEach(part -> {
                        comeback.incrementAndGet();
                        total.addAll(part);
                    });

            if (workerCount != comeback.intValue()) {
                break;
            }

            // #2. Redis 연동을 위한 초기화
            comeback.set(0);
            ranges.clear();
        }

        if (filterIn == null) {
            filterIn = v -> true;
        }

        return total.stream() //
                .filter(filterIn) //
                .collect(Collectors.toList());
    }

    /**
     * Redis database 의 키/값을 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 4. 8.      parkjunhong77@gmail.com         최초 작성
     * </pre>
     *
     * @param op
     *            실행 연산자
     * @param keys
     *            키 목록
     * @param partition
     *            조회 분할 크기
     * @return
     *
     * @since 2020. 4. 8.
     * 
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     */
    protected Map<K, V> mgetWithKey(@NotNull ValueOperations<K, V> op, @NotNull Collection<K> keys, @Min(1) int partition) {

        List<ArrayList<K>> keysPartitions = new ArrayList<>();

        ArrayList<K> keysPart = new ArrayList<>();
        int readCount = 0;
        for (K key : keys) {
            readCount++;
            keysPart.add(key);
            // 분할하려는 크기와 동일한 경우
            if ((readCount % partition) == 0) {
                keysPartitions.add(keysPart);

                // 초기화
                keysPart = new ArrayList<>();
                readCount = 0;
            }
        }

        // 남은 키 추가.
        if (keysPart.size() > 0) {
            keysPartitions.add(keysPart);
        }

        FIFOMap<K, V> keyValues = new FIFOMap<>();
        keysPartitions.parallelStream() //
                .map(pKeys -> {
                    List<V> pValues = op.multiGet(pKeys);

                    Iterator<K> kItr = pKeys.iterator();
                    Iterator<V> vItr = pValues.iterator();

                    Map<K, V> kv = new FIFOMap<>();
                    K k = null;
                    V v = null;
                    // 키에 해당하는 값이 있는 경우만 추가
                    while (kItr.hasNext() && vItr.hasNext()) {
                        k = kItr.next();
                        v = vItr.next();
                        if (v != null) {
                            kv.put(k, v);
                        }
                    }
                    return kv;
                }) //
                .forEach(kv -> {
                    keyValues.putAll(kv);
                });

        return keyValues;
    }

    protected RedisSerializer<String> stringSerializer() {
        return RedisSerializer.string();
    }

    protected abstract RedisSerializer<?> valueSerializer();
}
