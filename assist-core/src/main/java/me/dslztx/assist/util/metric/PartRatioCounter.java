package me.dslztx.assist.util.metric;

import me.dslztx.assist.util.CollectionAssist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dslztx
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PartRatioCounter {

    ConcurrentHashMap<String, ConcurrentHashMap<String, AtomicInteger>> partStat = new ConcurrentHashMap<String, ConcurrentHashMap<String, AtomicInteger>>();


    public void incrPart(String groupName, String partKey) {
        partStat.putIfAbsent(groupName, new ConcurrentHashMap<String, AtomicInteger>());

        ConcurrentHashMap<String, AtomicInteger> groupStat = partStat.get(groupName);

        groupStat.putIfAbsent(partKey, new AtomicInteger(0));

        groupStat.get(partKey).incrementAndGet();
    }

    public List<PartRatio> generatePartRatioList(ConcurrentHashMap<String, AtomicInteger> groupStat) {
        List<PartRatio> result = new ArrayList<PartRatio>();

        int sum = 0;
        for (String key : groupStat.keySet()) {
            sum += groupStat.get(key).get();
        }

        if (sum == 0) {
            return result;
        }

        double ratioPercent;

        for (String key : groupStat.keySet()) {
            ratioPercent = groupStat.get(key).get() * 1.0 / sum * 100;

            result.add(new PartRatio(key, (int) ratioPercent));
        }

        return result;
    }

    public Map<String, List<PartRatio>> obtainGroupPartRatioMap() {
        Map<String, List<PartRatio>> result = new HashMap<String, List<PartRatio>>();

        if (CollectionAssist.isEmpty(partStat.keySet())) {
            return result;
        }

        for (String groupName : partStat.keySet()) {
            result.put(groupName, generatePartRatioList(partStat.get(groupName)));
        }

        return result;
    }


    public static class PartRatio {
        String partKey;

        /**
         * 乘以100
         */
        int partRatioPercent;

        public PartRatio(String partKey, int partRatioPercent) {
            this.partKey = partKey;

            this.partRatioPercent = partRatioPercent;
        }

        public String getPartKey() {
            return partKey;
        }

        public void setPartKey(String partKey) {
            this.partKey = partKey;
        }

        public int getPartRatioPercent() {
            return partRatioPercent;
        }

        public void setPartRatioPercent(int partRatioPercent) {
            this.partRatioPercent = partRatioPercent;
        }
    }
}
