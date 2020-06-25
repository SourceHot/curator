/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.curator;

import org.apache.zookeeper.KeeperException;

/**
 * Abstracts the policy to use when retrying connections
 * <p>
 * 重试策略接口
 */
public interface RetryPolicy {
    /**
     * Called when an operation has failed for some reason. This method should return
     * true to make another attempt.
     * <p>
     * 是否可以重试
     *
     * @param retryCount    the number of times retried so far (0 the first time)
     *                      重试次数
     * @param elapsedTimeMs the elapsed time in ms since the operation was attempted
     * @param sleeper       use this to sleep - DO NOT call Thread.sleep
     *                      睡眠接口
     * @return true/false
     */
    boolean allowRetry(int retryCount, long elapsedTimeMs, RetrySleeper sleeper);

    /**
     * Called when an operation has failed with a specific exception. This method
     * should return true to make another attempt.
     * 默认的是否可以重试.判断方式
     *
     * @param exception the cause that this operation failed
     * @return true/false
     */
    default boolean allowRetry(Throwable exception) {
        if (exception instanceof KeeperException) {
            final int rc = ((KeeperException) exception).code().intValue();
            return (rc == KeeperException.Code.CONNECTIONLOSS.intValue()) ||
                    (rc == KeeperException.Code.OPERATIONTIMEOUT.intValue()) ||
                    (rc == KeeperException.Code.SESSIONMOVED.intValue()) ||
                    (rc == KeeperException.Code.SESSIONEXPIRED.intValue());
        }
        return false;
    }
}
