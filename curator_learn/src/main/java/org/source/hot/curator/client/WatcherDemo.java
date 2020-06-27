package org.source.hot.curator.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class WatcherDemo {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().sessionTimeoutMs(3000).connectString("127.0.0.1:32771")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

        // 启动
        curatorFramework.start();
        System.out.println("开始了");
        addListenerWithNode(curatorFramework);
        System.in.read();
    }

    private static void addListenerWithNode(CuratorFramework framework) throws Exception {
        NodeCache nodeCache = new NodeCache(framework, "/watch", false);
        NodeCacheListener nodeCacheListener = () -> {
            System.out.println("数据发生变化");
            String path = nodeCache.getCurrentData().getPath();
            System.out.println("变化的节点地址\t" + path);
            String string = new String(nodeCache.getCurrentData().getData());
            System.out.println("变化的数据\t" + string);
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    private static void addListenerWithPathChildren(CuratorFramework framework) throws Exception {
        PathChildrenCache childrenCache = new PathChildrenCache(framework, "/watch", true);
        PathChildrenCacheListener pathChildrenCacheListener = (client, event) -> {
            PathChildrenCacheEvent.Type type = event.getType();
            System.out.println("当前的事件类型: " + type.name());
            String s = new String(event.getData().getData());
            System.out.println("数据值: " + s);
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        childrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }
}
