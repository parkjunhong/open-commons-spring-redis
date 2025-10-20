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
 * Date  : 2020. 12. 1. 오후 4:41:47
 *
 * Author: Park_Jun_Hong_(parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.redis.lettuce;

import javax.validation.constraints.NotNull;

import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.validation.annotation.Validated;

import open.commons.core.utils.ExceptionUtils;
import open.commons.spring.redis.AbstractRedisDao;
import open.commons.spring.redis.RedisConfig;
import open.commons.spring.redis.RedisConfig.ConnectionTarget;
import open.commons.spring.redis.RedisConnectionConfig;
import open.commons.spring.redis.RedisTuningConfig;

import io.lettuce.core.ReadFrom;

/**
 * {@link LettuceConnectionFactory} 를 지원하는 Redis Dao 클래스.
 * 
 * @since 2020. 12. 1.
 * @version 0.1.0
 * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
 */
@Validated
public abstract class AbstractLettuceRedisDao<K, V> extends AbstractRedisDao<K, V, LettuceConnectionFactory> {

    /** Redis 접속 정보 */
    @NotNull
    protected RedisConfig redisConfig;

    /** Redis 명령어별 성능 향상을 위한 설정 */
    protected RedisTuningConfig tuningConfig;

    private LettuceConnectionFactory connectionFactory;

    /**
     * 
     * @since 2020. 12. 1.
     */
    public AbstractLettuceRedisDao() {
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
     * @see open.commons.spring.redis.AbstractRedisDao#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        final ConnectionTarget target = redisConfig.getTarget();
        switch (target) {
            case REDIS:
                LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder() //
                        .readFrom(ReadFrom.ANY).build();

                RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
                serverConfig.setHostName(redisConfig.getMaster().getIp());
                serverConfig.setPort(redisConfig.getMaster().getPort());
                serverConfig.setPassword(redisConfig.getMaster().getAuthentication());

                connectionFactory = new LettuceConnectionFactory(serverConfig, clientConfig);
                break;
            case SENTINEL:
                RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration() //
                        .master(redisConfig.getSentinel().getMaster());

                for (RedisConnectionConfig con : redisConfig.getSentinel().getMembers()) {
                    sentinelConfig.sentinel(con.getIp(), con.getPort());
                }

                sentinelConfig.setPassword(redisConfig.getSentinel().getMasterAuth());

                connectionFactory = new LettuceConnectionFactory(sentinelConfig);
                break;
            default:
                // unreachable code. 2020/12/01
                throw ExceptionUtils.newException(IllegalArgumentException.class, "지원하지 않는 Redis 대상입니다. 입력={}", target.get());
        }

        // #1. 빈 생성시점에 database 결정
        connectionFactory.setDatabase(getDatabase());
        this.redisTemplate = createRedisTemplate(connectionFactory);
        // #2. LettuceConnectionFactory.afterPropertiesSet()에서 Connection 생성
        connectionFactory.afterPropertiesSet();
        this.redisTemplate.afterPropertiesSet();

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
     * @return
     *
     * @since 2020. 12. 1.
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     *
     * @see open.commons.spring.redis.AbstractRedisDao#getRedisConnectionFactory()
     */
    @Override
    public final LettuceConnectionFactory getRedisConnectionFactory() {
        return this.connectionFactory;
    }

    /**
     * Redis 서버 연결 정보를 설정한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 12. 1.		parkjunhong77@gmail.com			최초 작성
     * </pre>
     *
     * @param redisConfig
     *
     * @since 2020. 12. 1.
     * @version 0.1.0
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     * 
     * @see #redisConfig
     */
    public abstract void setRedisConfig(RedisConfig redisConfig);

    /**
     * Redis 명령어별 성능 향상을 위한 설정을 적용한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 12. 3.		parkjunhong77@gmail.com			최초 작성
     * </pre>
     *
     * @param tuningConfig
     *
     * @since 2020. 12. 3.
     * @version 0.1.0
     * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
     * 
     * @see #ConfigurationProperties
     */
    public abstract void setTuningConfig(RedisTuningConfig tuningConfig);

}
