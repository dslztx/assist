package me.dslztx.assist.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 参考"org.springframework.core.io.ClassPathResource"
 * 
 * @author dslztx
 */
public class ClassPathResourceAssist {

    private static final Logger logger = LoggerFactory.getLogger(ClassPathResourceAssist.class);

    /**
     * 类资源分为两种： <br/>
     *
     * 1）JAR包外，可返回File对象，也可返回InputStream对象<br/>
     *
     * 2)JAR包内，可返回InputStream对象，如果返回File对象，类似“file:/home/dslztx/xxx.jar!/log4j.properties”，但是如果尝试通过该File对象创建一个FileInputStream流，则会报“Method
     * threw 'java.io.FileNotFoundException' exception.”异常<br/>
     *
     * 因此，统一返回InputStream，而不返回File，在外层根据InputStream读取组装相应的目标对象
     *
     */
    public static List<InputStream> locateInputStreams(String name) {
        List<InputStream> inputStreams = new ArrayList<InputStream>();

        if (StringAssist.isBlank(name)) {
            return inputStreams;
        }

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
            inputStreams = locateInputStreams(cl, name);
        } catch (Exception e) {
            logger.error("", e);
        }

        if (CollectionAssist.isEmpty(inputStreams)) {
            try {
                cl = ClassPathResourceAssist.class.getClassLoader();
                inputStreams = locateInputStreams(cl, name);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        if (CollectionAssist.isEmpty(inputStreams)) {
            try {
                cl = ClassLoader.getSystemClassLoader();
                inputStreams = locateInputStreams(cl, name);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return inputStreams;
    }

    /**
     * 类资源分为两种： <br/>
     *
     * 1）JAR包外，可返回File对象，也可返回InputStream对象<br/>
     *
     * 2)JAR包内，可返回InputStream对象，如果返回File对象，类似“file:/home/dslztx/xxx.jar!/log4j.properties”，但是如果尝试通过该File对象创建一个FileInputStream流，则会报“Method
     * threw 'java.io.FileNotFoundException' exception.”异常<br/>
     *
     * 因此，统一返回InputStream，而不返回File，在外层根据InputStream读取组装相应的目标对象
     *
     */
    public static InputStream locateInputStream(String name) {
        InputStream in = null;

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
            in = locateInputStream(cl, name);
        } catch (Exception e) {
            logger.error("", e);
        }

        if (ObjectAssist.isNull(in)) {
            try {
                cl = ClassPathResourceAssist.class.getClassLoader();
                in = locateInputStream(cl, name);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        if (ObjectAssist.isNull(in)) {
            try {
                cl = ClassLoader.getSystemClassLoader();
                in = locateInputStream(cl, name);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return in;
    }

    private static InputStream locateInputStream(ClassLoader cl, String name) throws IOException {
        if (ObjectAssist.isNull(cl))
            return null;

        URL url = cl.getResource(name);
        if (ObjectAssist.isNotNull(url)) {
            return url.openStream();
        }

        return null;
    }

    private static List<InputStream> locateInputStreams(ClassLoader cl, String name) throws IOException {
        List<InputStream> inputStreams = new ArrayList<InputStream>();

        if (ObjectAssist.isNull(cl)) {
            return inputStreams;
        }

        Enumeration<URL> urls = cl.getResources(name);

        if (ObjectAssist.isNotNull(urls)) {
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                inputStreams.add(url.openStream());
            }
        }

        return inputStreams;
    }

    public static List<File> locateFilesNotInJar(String name) {
        List<File> files = new ArrayList<File>();

        if (StringAssist.isBlank(name)) {
            return files;
        }

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
            files = locateFilesNotInJar(cl, name);
        } catch (Exception e) {
            logger.error("", e);
        }

        if (CollectionAssist.isEmpty(files)) {
            try {
                cl = ClassPathResourceAssist.class.getClassLoader();
                files = locateFilesNotInJar(cl, name);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        if (CollectionAssist.isEmpty(files)) {
            try {
                cl = ClassLoader.getSystemClassLoader();
                files = locateFilesNotInJar(cl, name);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return files;
    }

    private static List<File> locateFilesNotInJar(ClassLoader cl, String name) throws IOException {
        List<File> files = new ArrayList<File>();

        if (ObjectAssist.isNull(cl)) {
            return files;
        }

        Enumeration<URL> urls = cl.getResources(name);

        if (ObjectAssist.isNotNull(urls)) {
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                File file = new File(url.getFile());
                if (file.getPath().contains("jar!")) {
                    // 认为该资源属于JAR包
                    continue;
                }

                files.add(file);
            }
        }

        return files;
    }

    public static File locateFileNotInJar(String name) {
        File file = null;

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
            file = locateFileNotInJar(cl, name);
        } catch (Exception e) {
            logger.error("", e);
        }

        if (ObjectAssist.isNull(file)) {
            try {
                cl = ClassPathResourceAssist.class.getClassLoader();
                file = locateFileNotInJar(cl, name);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        if (ObjectAssist.isNull(file)) {
            try {
                cl = ClassLoader.getSystemClassLoader();
                file = locateFileNotInJar(cl, name);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return file;
    }

    private static File locateFileNotInJar(ClassLoader cl, String name) {
        if (ObjectAssist.isNull(cl))
            return null;

        URL url = cl.getResource(name);
        if (ObjectAssist.isNotNull(url)) {
            File file = new File(url.getFile());
            if (file.getPath().contains("jar!")) {
                // 认为该资源属于JAR包
                return null;
            }

            return file;
        }

        return null;
    }
}
