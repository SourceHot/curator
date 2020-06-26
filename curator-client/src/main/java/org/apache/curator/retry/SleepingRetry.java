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
package org.apache.curator.retry;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;

import java.util.concurrent.TimeUnit;

abstract class SleepingRetry implements RetryPolicy {
    private final int n;

    protected SleepingRetry(int n) {
        this.n = n;
    }

    // made public for testing
    public int getN() {
        return n;
    }

    /**
     * 是否可以重试
     *
     * @param retryCount    the number of times retried so far (0 the first time)
     * @param elapsedTimeMs the elapsed time in ms since the operation was attempted
     * @param sleeper       use this to sleep - DO NOT call Thread.sleep
     * @return
     */
    public boolean allowRetry(int retryCount, long elapsedTimeMs, RetrySleeper sleeper) {
        if (retryCount < n) {
            try {
                sleeper.sleepFor(getSleepTimeMs(retryCount, elapsedTimeMs), TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 睡眠时间(毫秒)
     *
     * @param retryCount    重试次数
     * @param elapsedTimeMs 延迟时间
     * @return
     */
    protected abstract long getSleepTimeMs(int retryCount, long elapsedTimeMs);
}
