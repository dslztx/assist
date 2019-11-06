package me.dslztx.assist.util.metric;

/**
 * @author dslztx
 */
@SuppressWarnings("unused")
public class PartRatioStatistic extends Statistic<PartRatioCounter> {

    PartRatioCounter stat = new PartRatioCounter();

    public void incrPart(String partKey) {
        doReadLock();
        try {
            stat.incrPart(partKey);
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
