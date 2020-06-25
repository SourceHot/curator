package org.source.hot.curator.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Demo01 {

    public static void main(String[] args) {
        CuratorFramework build = CuratorFrameworkFactory.builder().sessionTimeoutMs(3000).connectString("127.0.0.1:32775")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
    }
}
