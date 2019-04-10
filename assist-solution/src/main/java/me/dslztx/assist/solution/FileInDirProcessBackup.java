package me.dslztx.assist.solution;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.pattern.strategy.FileProcessor;
import me.dslztx.assist.pattern.strategy.IncludeFilter;
import me.dslztx.assist.util.ArrayAssist;
import me.dslztx.assist.util.FileAssist;
import me.dslztx.assist.util.ObjectAssist;

public class FileInDirProcessBackup implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(FileInDirProcessBackup.class);

    /**
     * 从设计意图上说，本任务的run()只有一个线程执行，stop()方法可以由另外一个线程执行，因此，SimpleDateFormat没有线程安全问题的<br/>
     * 因此，这里使用ThreadLocal不是必需的
     */
    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };

    File srcDir;

    File destDir;

    FileProcessor fileProcessor;

    IncludeFilter<File> includeFilter;

    volatile boolean stop = false;

    ReadWriteLock lock = new ReentrantReadWriteLock();

    public FileInDirProcessBackup(File srcDir, File destDir, IncludeFilter<File> includeFilter,
        FileProcessor fileProcessor) {
        this.srcDir = srcDir;
        this.destDir = destDir;
        this.includeFilter = includeFilter;
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void run() {
        while (true) {
            lock.readLock().lock();
            try {
                if (stop) {
                    return;
                }

                File[] files = FileAssist.listFilesModifyTimeDesc(srcDir);

                boolean firstFile = true;

                if (ArrayAssist.isNotEmpty(files)) {
                    for (File file : files) {
                        if (firstFile) {
                            // 默认在当前轮次中排除掉修改时间最近的文件，避免操作还在更新中的文件
                            firstFile = false;
                            continue;
                        }

                        if (!includeFilter.include(file)) {
                            continue;
                        }

                        try {
                            if (ObjectAssist.isNull(fileProcessor)) {
                                FileUtils.moveFile(file, new File(destDir, file.getName() + obtainRealTimeSuffix()));
                                logger.info("file {} move successfully", file.getName());
                            } else {
                                fileProcessor.process(file);
                                FileUtils.moveFile(file, new File(destDir, file.getName() + obtainRealTimeSuffix()));
                                logger.info("file {} process and move successfully", file.getName());
                            }
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
                }
            } finally {
                lock.readLock().unlock();
            }

            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    public String obtainRealTimeSuffix() {
        return simpleDateFormatThreadLocal.get().format(new Date());
    }

    public void stop() {
        lock.writeLock().lock();
        try {
            stop = true;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
