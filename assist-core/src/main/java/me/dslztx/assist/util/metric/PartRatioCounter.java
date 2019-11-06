package me.dslztx.assist.util.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dslztx
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PartRatioCounter {

    ConcurrentHashMap<String, AtomicInteger> partStat = new ConcurrentHashMap<String, AtomicInteger>();


    public void incrPart(String partKey) {
        partStat.putIfAbsent(partKey, new AtomicInteger(0));

        partStat.get(partKey).incrementAndGet();
    }

    public List<PartRatio> obtainPartRatioList() {
        List<PartRatio> result = new ArrayList<PartRatio>();

        int sum = 0;
        for (String key : partStat.keySet()) {
            sum += partStat.get(key).get();
        }

        if (sum == 0) {
            return result;
        }

        double ratioPercent;

        for (String key : partStat.keySet()) {
            ratioPercent = partStat.get(key).get() * 1.0 / sum * 100;

            result.add(new PartRatio(key, (int) ratioPercent));
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
