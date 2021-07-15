package me.dslztx.assist.util.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参考com.alibaba.csp.sentinel.concurrent.NamedThreadFactory
 */
public class NamedThreadFactory implements ThreadFactory {

    private final ThreadGroup group;

    private final AtomicInteger threadNumber;

    private final String namePrefix;

    private final boolean daemon;

    public NamedThreadFactory(String namePrefix, boolean daemon) {
        this.namePrefix = namePrefix;
        this.daemon = daemon;

        this.threadNumber = new AtomicInteger(1);

        SecurityManager s = System.getSecurityManager();
        this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.group, r, this.namePrefix + "-thread-" + this.threadNumber.getAndIncrement());

        t.setDaemon(this.daemon);
        return t;
    }
}