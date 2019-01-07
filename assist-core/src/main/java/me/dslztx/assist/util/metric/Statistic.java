package me.dslztx.assist.util.metric;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Statistic<T> {

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    protected void doReadLock() {
        lock.readLock().lock();
    }

    protected void doReadUnLock() {
        lock.readLock().unlock();
    }

    protected void doWriteLock() {
        lock.writeLock().lock();
    }

    protected void doWriteUnLock() {
        lock.writeLock().unlock();
    }

    protected abstract T obtain();

    protected abstract void reset();

    /**
     * 一般是一个定时的非工作线程执行这个方法，挂起等待工作线程持有的“读”锁，对性能影响不大；然后方法体非常简单，立刻返回，因此，工作线程不会因为等待“写”锁而挂起很长时间
     */
    public T obtainAndReset() {
        doWriteLock();
        try {
            T obj = obtain();

            reset();

            return obj;
        } finally {
            doWriteUnLock();
        }
    }

}
