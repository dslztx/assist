package me.dslztx.assist.util.concurrent;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.FixedRateScheduleAssist;
import me.dslztx.assist.util.ObjectAssist;

/**
 * 1、线程级别“文件锁”，RandomAccessFile -> FileChannel -> FileLock是进程级别的"文件锁"<br/>
 * 2、当下的并发粒度不够细，不同的文件锁使用同一个lockObj的synchronized锁作为守门人。不过轻度使用场景，够用
 */
@Slf4j
public class FileLockAssist {

    private static final Object lockObj = new Object();

    private static Map<String, FileLocker> map = new HashMap<>();

    static {
        cleanMemory();
    }

    private static void cleanMemory() {

        FixedRateScheduleAssist.submitFixedRateJob(new Runnable() {
            @Override
            public void run() {
                synchronized (lockObj) {
                    if (map.keySet().size() > 10000) {

                        Set<String> toDel = new HashSet<String>();

                        FileLocker fileLocker = null;
                        for (String key : map.keySet()) {
                            fileLocker = map.get(key);

                            if (ObjectAssist.isNull(fileLocker) || fileLocker.notLocked()) {
                                toDel.add(key);
                            }
                        }

                        map.keySet().removeAll(toDel);
                    }
                }
            }
        }, 1, 1, TimeUnit.HOURS);
    }

    public static boolean tryLock(File file) {
        if (ObjectAssist.isNull(file)) {
            throw new RuntimeException("file is null");
        }

        synchronized (lockObj) {
            return lock0(file);
        }
    }

    private static boolean lock0(File file) {
        String path = file.getAbsolutePath();

        FileLocker fileLocker = map.get(path);

        if (ObjectAssist.isNull(fileLocker)) {
            map.put(path, new FileLocker(Thread.currentThread(), 1));
            return true;
        }

        if (fileLocker.notLocked()) {
            fileLocker.setThread(Thread.currentThread());
            fileLocker.setCnt(1);
        }

        if (fileLocker.getThread() == Thread.currentThread()) {
            fileLocker.incr();
            return true;
        } else {
            return false;
        }
    }

    public static boolean tryRelease(File file) {
        if (ObjectAssist.isNull(file)) {
            throw new RuntimeException("file is null");
        }

        synchronized (lockObj) {
            if (notLockedByMyself(file)) {
                throw new RuntimeException("invalid release");
            }

            FileLocker fileLocker = map.get(file.getAbsolutePath());
            fileLocker.decr();

            if (fileLocker.getCnt() == 0) {
                fileLocker.setThread(null);
                lockObj.notifyAll();

                return true;
            } else {
                return false;
            }
        }
    }

    private static boolean notLockedByMyself(File file) {
        String path = file.getAbsolutePath();

        if (ObjectAssist.isNull(map.get(path))) {
            return true;
        }

        FileLocker fileLocker = map.get(path);
        if (fileLocker.getThread() != Thread.currentThread()) {
            return true;
        }

        return false;
    }

    public static void lock(File file) throws InterruptedException {
        if (ObjectAssist.isNull(file)) {
            throw new RuntimeException("file is null");
        }

        synchronized (lockObj) {
            if (lock0(file)) {
                return;
            }

            while (true) {
                try {
                    lockObj.wait();
                } catch (InterruptedException e) {
                    throw e;
                }

                if (lock0(file)) {
                    break;
                }
            }
        }
    }

    public static void release(File file) {
        tryRelease(file);
    }
}

@Data
class FileLocker {
    Thread thread;

    int cnt;

    public FileLocker(Thread thread, int cnt) {
        this.thread = thread;
        this.cnt = cnt;
    }

    public void incr() {
        cnt++;
    }

    public void decr() {
        cnt--;
    }

    public boolean notLocked() {
        return cnt == 0;
    }
}
