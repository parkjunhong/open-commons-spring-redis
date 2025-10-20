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
 * Date  : 2020. 12. 1. 오후 4:47:24
 *
 * Author: Park_Jun_Hong_(parkjunhong77@gmail.com)
 * 
 */

package open.commons.spring.redis;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * 서비스 연결 정보
 * 
 * @since 2020. 12. 1.
 * @version 0.1.0
 * @author Park_Jun_Hong_(parkjunhong77@gmail.com)
 */
@Validated
public class RedisConnectionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 서비스 접속 IP */
    @NotNull
    protected String ip;

    /** 서바스 접속 Port */
    @Min(1)
    @Max(65535)
    protected int port;

    /**
     * Redis/Sentinal 인증 정보 (-a 옵션)
     */
    protected String authentication;

    /**
     * 
     * @since 2020. 12. 1.
     */
    public RedisConnectionConfig() {
    }

    /**
     *
     * @return the authentication
     *
     * @since 2020. 12. 1.
     */
    public String getAuthentication() {
        return authentication;
    }

    /**
     *
     * @return the ip
     *
     * @since 2020. 12. 1.
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @return the port
     *
     * @since 2020. 12. 1.
     */
    public int getPort() {
        return port;
    }

    /**
     * @param authentication
     *            the authentication to set
     *
     * @since 2020. 12. 1.
     */
    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    /**
     * @param ip
     *            the ip to set
     *
     * @since 2020. 12. 1.
     */
    public void setIp(@NotNull String ip) {
        this.ip = ip;
    }

    /**
     * @param port
     *            the port to set
     *
     * @since 2020. 12. 1.
     */
    public void setPort(@Min(1) @Max(65535) int port) {
        this.port = port;
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
        builder.append("RedisConnectionConfig [ip=");
        builder.append(ip);
        builder.append(", port=");
        builder.append(port);
        builder.append(", authentication=");
        builder.append(authentication);
        builder.append("]");
        return builder.toString();
    }

}
