package me.dslztx.assist.util.metric;

/**
 * @author dslztx
 */
@SuppressWarnings("unused")
public class PartRatioStatistic extends Statistic<PartRatioCounter> {

    private PartRatioCounter stat = new PartRatioCounter();

    public void incrPart(String groupName, String partKey) {
        doReadLock();
        try {
            stat.incrPart(groupName, partKey);
        } finally {
            doReadUnLock();
        }
    }

    @Override
    protected PartRatioCounter obtain() {
        return stat;
    }

    @Override
    protected void reset() {
        stat = new PartRatioCounter();
    }
}
