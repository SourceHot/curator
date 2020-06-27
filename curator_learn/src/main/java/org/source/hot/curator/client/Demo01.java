package org.source.hot.curator.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

public class Demo01 {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().sessionTimeoutMs(3000).connectString("127.0.0.1:32771")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

        // 启动
        curatorFramework.start();
        // CRUD
//        createDate(curatorFramework);
//        updateDate(curatorFramework);
//        deleteDate(curatorFramework);

//        Stat stat = getStat(curatorFramework);
        setAcl(curatorFramework);
        System.out.println("done");

    }

    private static void setAcl(CuratorFramework framework) throws Exception {
        List<ACL> list = new ArrayList<>();
        // acl , 权限,id(账号密码)
        ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
        list.add(acl);
        framework.create().withACL(list).forPath("/data/acl_1", "acl".getBytes());
    }

    /**
     * 创建
     *
     * @param framework
     */
    private static void createDate(CuratorFramework framework) throws Exception {
        framework.create().creatingParentContainersIfNeeded().inBackground().forPath("/data/node_1", "test".getBytes());
    }

    /**
     * 修改
     *
     * @param framework
     * @throws Exception
     */
    private static void updateDate(CuratorFramework framework) throws Exception {
        framework.setData().forPath("/data/node_1", "set_data".getBytes());
    }

    private static void deleteDate(CuratorFramework framework) throws Exception {
        framework.delete().withVersion(2).forPath("/data/node_1");
    }

    private static Stat getStat(CuratorFramework framework) throws Exception {
        Stat stat = new Stat();
        framework.getData().storingStatIn(stat).forPath("/data/node_1");
        return stat;
    }
}
