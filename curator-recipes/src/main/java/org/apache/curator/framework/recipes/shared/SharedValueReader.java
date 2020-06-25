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
package org.apache.curator.framework.recipes.shared;

import org.apache.curator.framework.listen.Listenable;

/**
 * Abstracts a shared value and allows listening for changes to the value
 */
public interface SharedValueReader {
    /**
     * Return the current value of the count
     *
     * @return count
     */
    public byte[] getValue();

    /**
     * Return the current version and value
     *
     * @return version/value
     */
    public VersionedValue<byte[]> getVersionedValue();

    /**
     * Returns the listenable
     *
     * @return listenable
     */
    public Listenable<SharedValueListener> getListenable();
}
