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
package org.apache.curator.framework.api;

import org.apache.curator.framework.CuratorFrameworkFactory;

public interface PathAndBytesable<T> {
    /**
     * Commit the currently building operation using the given path and data
     * <p>
     * 创建一个节点并且赋予数据
     *
     * @param path the path 节点地址
     * @param data the data 数据
     * @return operation result if any
     * @throws Exception errors
     */
    T forPath(String path, byte[] data) throws Exception;

    /**
     * Commit the currently building operation using the given path and the default data
     * for the client (usually a byte[0] unless changed via
     * {@link CuratorFrameworkFactory.Builder#defaultData(byte[])}).
     * <p>
     * 创建节点.
     *
     * @param path the path 节点地址
     * @return operation result if any
     * @throws Exception errors
     */
    T forPath(String path) throws Exception;
}
