package me.dslztx.assist.client.zookeeper;

import java.util.HashMap;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ObjectAssist;
import me.dslztx.assist.util.StringAssist;

public class ZooKeeperAssist {

    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperAssist.class);

    public static Map<String, String> traverseLeafNode(CuratorFramework curatorFramework, String rootPath) {
        Map<String, String> result = new HashMap<String, String>();
        if (ObjectAssist.isNull(curatorFramework) || StringAssist.isBlank(rootPath)) {
            return result;
        }

        StringBuilder sb = new StringBuilder();
        int pos = rootPath.length() - 1;
        while (pos >= 0) {
            if (rootPath.charAt(pos) == '/') {
                pos--;
            } else {
                break;
            }
        }

        if (pos <= 0) {
            // pos=0也不满足，是因为路径必须以“/”开头
            return result;
        }

        rootPath = rootPath.substring(0, pos + 1);

        try {
            traverseLeafNode0(curatorFramework, rootPath, result);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    public static void traverseLeafNode0(CuratorFramework curatorFramework, String path, Map<String, String> map)
        throws Exception {

        String[] childPaths = curatorFramework.getChildren().forPath(path).toArray(new String[0]);
        if (childPaths.length == 0) {
            map.put(path, new String(curatorFramework.getData().forPath(path)));
        }

        for (String childPath : childPaths) {
            traverseLeafNode0(curatorFramework, path + "/" + childPath, map);
        }
    }
}
