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
 * Date  : 2020. 12. 1. 오후 4:46:13
 *
 * Author: Park_Jun_Hong_(parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * Redis 설정
 * 
 * @since 2020. 12. 1.
 * @version 0.1.0
 * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
 */
@Validated
public class RedisConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Redis 서버 접속 대상 */
    private ConnectionTarget target;
    /** Redis 'Master' 서버 연결 정보 */
    private RedisConnectionConfig master;
    /** Redis Sentinel 구성 정보 */
    private RedisSentinel sentinel;

    /**
     * 
     * @since 2020. 12. 1.
     */
    public RedisConfig() {
    }

    /**
     *
     * @return the master
     *
     * @since 2020. 12. 1.
     */
    public RedisConnectionConfig getMaster() {
        return master;
    }

    /**
     *
     * @return the sentinel
     *
     * @since 2020. 12. 1.
     */
    public RedisSentinel getSentinel() {
        return sentinel;
    }

    /**
     *
     * @return the target
     *
     * @since 2020. 12. 1.
     */
    public ConnectionTarget getTarget() {
        return target;
    }

    /**
     * @param master
     *            the master to set
     *
     * @since 2020. 12. 1.
     */
    public void setMaster(RedisConnectionConfig master) {
        this.master = master;
    }

    /**
     * @param sentinel
     *            the sentinel to set
     *
     * @since 2020. 12. 1.
     */
    public void setSentinel(RedisSentinel sentinel) {
        this.sentinel = sentinel;
    }

    /**
     * @param target
     *            the target to set
     *
     * @since 2020. 12. 1.
     */
    public void setTarget(String target) {
        this.target = ConnectionTarget.get(target);
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RedisConfig [target=");
        builder.append(target);
        builder.append(", master=");
        builder.append(master);
        builder.append(", sentinel=");
        builder.append(sentinel);
        builder.append("]");
        return builder.toString();
    }

    public static enum ConnectionTarget {
        /** 'redis' */
        REDIS("redis"), //
        /** 'sentinel' */
        SENTINEL("sentinel") //
        ;

        private String target;

        private ConnectionTarget(String target) {
            this.target = target;
        }

        /**
         *
         * @return a string of an instance of {@link RedisConfig.ConnectionTarget}
         */
        public String get() {
            return this.target;
        }

        /**
         * 
         * @param target
         *            a string for {@link RedisConfig.ConnectionTarget} instance.
         *
         * @return an instance of {@link RedisConfig.ConnectionTarget}
         *
         * @see #get(String, boolean)
         */
        public static ConnectionTarget get(String target) {
            return get(target, false);
        }

        /**
         *
         * @param target
         *            a string for an instance of {@link RedisConfig.ConnectionTarget}.
         * @param ignoreCase
         *            ignore <code><b>case-sensitive</b></code> or not.
         *
         * @return an instance of {@link RedisConfig.ConnectionTarget}
         */
        public static ConnectionTarget get(String target, boolean ignoreCase) {

            if (target == null) {
                throw new IllegalArgumentException("'target' MUST NOT be null. input: " + target);
            }

            if (ignoreCase) {
                for (ConnectionTarget value : values()) {
                    if (value.target.equalsIgnoreCase(target)) {
                        return value;
                    }
                }
            } else {
                for (ConnectionTarget value : values()) {
                    if (value.target.equals(target)) {
                        return value;
                    }
                }
            }

            throw new IllegalArgumentException(
                    "Unexpected 'target' value of 'RedisConfig.ConnectionTarget'. expected: " + values0() + " & Ignore case-sensitive: " + ignoreCase + ", input: " + target);
        }

        private static List<String> values0() {

            List<String> valuesStr = new ArrayList<>();

            for (ConnectionTarget value : values()) {
                valuesStr.add(value.get());
            }

            return valuesStr;
        }

    }

    @Validated
    public static class RedisSentinel implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 'Master' 노드 이름. (기본값: mymaster) */
        @NotNull
        private String master = "mymaster";
        /** 'Master' 노드 비밀번호. */
        @NotNull
        private String masterAuth;
        /** Sentinel 서버 연결 정보 */
        @NotNull
        private List<RedisConnectionConfig> members;

        /**
         *
         * @return the master
         *
         * @since 2020. 12. 1.
         */
        public String getMaster() {
            return master;
        }

        /**
         *
         * @return the masterAuth
         *
         * @since 2020. 12. 1.
         */
        public String getMasterAuth() {
            return masterAuth;
        }

        /**
         *
         * @return the members
         *
         * @since 2020. 12. 1.
         */
        public List<RedisConnectionConfig> getMembers() {
            return members;
        }

        /**
         * @param master
         *            the master to set
         *
         * @since 2020. 12. 1.
         */
        public void setMaster(@NotNull String master) {
            this.master = master;
        }

        /**
         * @param masterAuth
         *            the masterAuth to set
         *
         * @since 2020. 12. 1.
         */
        public void setMasterAuth(@NotNull String masterAuth) {
            this.masterAuth = masterAuth;
        }

        /**
         * @param members
         *            the members to set
         *
         * @since 2020. 12. 1.
         */
        public void setMembers(@NotNull List<RedisConnectionConfig> members) {
            this.members = members;
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
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("RedisSentinel [master=");
            builder.append(master);
            builder.append(", masterAuth=");
            builder.append(masterAuth);
            builder.append(", members=");
            builder.append(members);
            builder.append("]");
            return builder.toString();
        }

    }
}
