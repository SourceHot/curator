# curator watcher
> author: [huifer](https://github.com/huifer)
>
> git_repo : [curator](https://github.com/SourceHot/curator)
>



## zookeeper 提供的监听事件
- `org.apache.zookeeper.Watcher.Event.EventType`

watcher 只能监听一次

- 事件类型如下
    - None: 无
    - NodeCreated: 节点创建
    - NodeDeleted: 节点删除
    - NodeDataChanged: 节点数据变更
    - NodeChildrenChanged: 节点子集数据变更
    - DataWatchRemoved: 数据监控删除
    - ChildWatchRemoved: 子集监控删除
    - PersistentWatchRemoved: 持久化的监控删除
    





## 实战



```java
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
}
```





- Cli 设置数据 `set /watch test`

- 控制台输出

```
数据发生变化
变化的节点地址	/watch
变化的数据	test
```

