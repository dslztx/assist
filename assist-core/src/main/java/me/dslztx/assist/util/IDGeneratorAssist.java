package me.dslztx.assist.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 分布式系统的唯一ID需要借助于redis，mysql等中间件，可直接使用“distributed-tool”等第三方包<br/>
 *
 * 同一台机器多线程情形下，不同线程获取到UUID可能一致，因此不能保证唯一性
 * 
 * @author dslztx
 * @refactored dslztx
 */
@SuppressWarnings("unused")
public class IDGeneratorAssist {

    private static ThreadLocal<Long> threadScopeId = new ThreadLocal<Long>() {
        public Long initialValue() {
            return 0L;
        }
    };

    private static AtomicLong applicationScopeId = new AtomicLong(0);

    /**
     * @return 线程唯一ID
     */
    public static Long obtainThreadScopeId() {
        Long id = threadScopeId.get();

        if (id.equals(Long.MAX_VALUE)) {
            threadScopeId.set(0L);
        } else {
            threadScopeId.set(id + 1);
        }
        return id;
    }

    /**
     * @return 应用唯一ID
     */
    public static Long obtainApplicationScopeId() {
        Long result = applicationScopeId.getAndIncrement();

        if (result.compareTo(0L) < 0) {
            synchronized (IDGeneratorAssist.class) {
                if (applicationScopeId.get() < 0L) {
                    applicationScopeId.set(0L);
                }

                result = applicationScopeId.getAndIncrement();
            }
        }

        return result;
    }

    public static String obtainApplicationScopeIdPrefix(String prefix) {
        if (StringAssist.isBlank(prefix)) {
            throw new RuntimeException("prefix can not be blank");
        }

        return prefix + obtainApplicationScopeId();
    }

    public static String obtainApplicationScopeIdSuffix(String suffix) {
        if (StringAssist.isBlank(suffix)) {
            throw new RuntimeException("suffix can not be blank");
        }

        return obtainApplicationScopeId() + suffix;
    }
}
