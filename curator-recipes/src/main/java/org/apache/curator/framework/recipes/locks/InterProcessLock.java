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
package org.apache.curator.framework.recipes.locks;

import java.util.concurrent.TimeUnit;

/**
 * NOTE: depending on its implementation, {@link #release()} may throw an exception if the current thread does not own the lock
 * 锁接口
 */
public interface InterProcessLock {
    /**
     * Acquire the mutex - blocking until it's available. Each call to acquire must be balanced by a call
     * to {@link #release()}
     * <p>
     * 上锁(获得锁)
     *
     * @throws Exception ZK errors, connection interruptions
     */
    void acquire() throws Exception;

    /**
     * Acquire the mutex - blocks until it's available or the given time expires. Each call to acquire that returns true must be balanced by a call
     * to {@link #release()}
     * <p>
     * 上锁(获得锁)
     *
     * @param time time to wait 等待时间
     * @param unit time unit 等待时间的单位
     * @return true if the mutex was acquired, false if not 是否获得锁
     * @throws Exception ZK errors, connection interruptions
     */
    boolean acquire(long time, TimeUnit unit) throws Exception;

    /**
     * Perform one release of the mutex.
     * <p>
     * 释放锁
     *
     * @throws Exception ZK errors, interruptions
     */
    void release() throws Exception;

    /**
     * Returns true if the mutex is acquired by a thread in this JVM
     * 当前线程是否获得锁
     *
     * @return true/false
     */
    boolean isAcquiredInThisProcess();
}
