package me.dslztx.assist.util;

import java.io.File;
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
// todo
public class ClassPathResourceAssist {

    private static final Logger logger = LoggerFactory.getLogger(ClassPathResourceAssist.class);

    public static List<File> locateFiles(String name) {
        List<File> files = new ArrayList<File>();

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
            files = locateFiles(cl, name);
        } catch (Exception e) {
        }

        if (CollectionAssist.isEmpty(files)) {
            cl = ClassPathResourceAssist.class.getClassLoader();

            files = locateFiles(cl, name);
        }

        if (CollectionAssist.isEmpty(files)) {
            cl = ClassLoader.getSystemClassLoader();
            files = locateFiles(cl, name);
        }

        return files;
    }

    public static File locateFile(String name) {
        File file = null;

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
            file = locateFile(cl, name);
        } catch (Exception e) {
        }

        if (ObjectAssist.isNull(file)) {

            cl = ClassPathResourceAssist.class.getClassLoader();

            file = locateFile(cl, name);
        }

        if (ObjectAssist.isNull(file)) {

            cl = ClassLoader.getSystemClassLoader();
            file = locateFile(cl, name);
        }

        return file;
    }

    public static List<InputStream> locateInputStreams(String name) {
        List<InputStream> files = new ArrayList<InputStream>();

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
            files = locateInputStreams(cl, name);
        } catch (Exception e) {
        }

        if (CollectionAssist.isEmpty(files)) {
            cl = ClassPathResourceAssist.class.getClassLoader();

            files = locateInputStreams(cl, name);
        }

        if (CollectionAssist.isEmpty(files)) {
            cl = ClassLoader.getSystemClassLoader();
            files = locateInputStreams(cl, name);
        }

        return files;
    }

    public static InputStream locateInputStream(String name) {
        InputStream in = null;

        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
            in = locateInputStream(cl, name);
        } catch (Exception e) {
        }

        if (ObjectAssist.isNull(in)) {
            cl = ClassPathResourceAssist.class.getClassLoader();

            in = locateInputStream(cl, name);
        }

        if (ObjectAssist.isNull(in)) {
            cl = ClassLoader.getSystemClassLoader();
            in = locateInputStream(cl, name);
        }

        return in;
    }

    private static InputStream locateInputStream(ClassLoader cl, String name) {
        if (cl != null) {
            try {
                URL url = cl.getResource(name);
                if (url != null) {
                    return url.openStream();
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return null;
    }

    private static List<InputStream> locateInputStreams(ClassLoader cl, String name) {
        List<InputStream> files = new ArrayList<InputStream>();
        if (cl != null) {
            try {
                Enumeration<URL> urls = cl.getResources(name);
                if (urls != null) {
                    while (urls.hasMoreElements()) {
                        URL url = urls.nextElement();
                        files.add(url.openStream());
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return files;
    }

    private static File locateFile(ClassLoader cl, String name) {
        if (cl != null) {
            try {
                URL url = cl.getResource(name);
                if (url != null) {
                    return new File(url.getFile());
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return null;
    }

    private static List<File> locateFiles(ClassLoader cl, String name) {
        List<File> files = new ArrayList<File>();
        if (cl != null) {
            try {
                Enumeration<URL> urls = cl.getResources(name);
                if (urls != null) {
                    while (urls.hasMoreElements()) {
                        URL url = urls.nextElement();
                        files.add(new File(url.getFile()));
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return files;
    }
}
