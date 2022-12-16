package me.dslztx.assist.solution;

import org.openjdk.jol.info.GraphLayout;

import me.dslztx.assist.util.ObjectAssist;

/**
 * 计算Java对象大小难点在于：<br/>
 * 1）存在很多对象布局策略（比如填充，对齐），要精确计算须穷尽所有策略<br/>
 * 2）是否开启指针压缩会跟1）联合起来影响最终对象大小计算<br/>
 * 
 * 存在很多方案，来自org.openjdk.jol:jol-core的方案相对较为精确<br/>
 * 
 * 但总的来说，计算Java对象大小偏向"定性"，而不要强求精确，实际使用场景也是如此<br/>
 * 
 */
public class JavaObjectSizeCalc {

    public static long cal(Object obj) {
        if (ObjectAssist.isNull(obj)) {
            return 0;
        }

        GraphLayout graphLayout = GraphLayout.parseInstance(obj);
        return graphLayout.totalSize();
    }
}
