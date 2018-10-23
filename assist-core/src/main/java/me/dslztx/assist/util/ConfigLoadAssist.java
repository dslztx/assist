package me.dslztx.assist.util;

import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.FileBasedBuilderParameters;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * org.apache.commons.configuration2.builder.fluent.Configurations的定位策略见： FileLocator#AgetLocationStrategy() <br/>
 *
 * 具体有：<br/>
 * 1)ProvidedURLLocationStrategy<br/>
 * 2)FileSystemLocationStrategy<br/>
 * 3)AbsoluteNameLocationStrategy<br/>
 * 4)BasePathLocationStrategy<br/>
 * 5)HomeDirectoryLocationStrategy<br/>
 * 6)CombinedLocationStrategy<br/>
 * 7)ClasspathLocationStrategy<br/>
 *
 * 用“String path”或者“URL url”指定路径，不要用“File file”（有歧义，一个file应该已经确定路径，但还是会去类路径下找）
 */
public class ConfigLoadAssist {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoadAssist.class);

    public static Configuration propConfig(String path) {
        if (StringAssist.isBlank(path)) {
            throw new RuntimeException("no config path");
        }
        try {
            Configurations configs = new Configurations();
            return configs.properties(path);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static Configuration propConfig(String path, String encoding) {
        if (StringAssist.isBlank(path)) {
            throw new RuntimeException("no config path");
        }

        try {
            Parameters parameters = new Parameters();
            FileBasedBuilderParameters paras = parameters.fileBased().setEncoding(encoding).setFileName(path);

            FileBasedConfigurationBuilder builder = new FileBasedConfigurationBuilder(PropertiesConfiguration.class);
            builder.configure(paras);

            return (Configuration)builder.getConfiguration();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * XML文档的字符编码规定为UTF-8
     */
    public static Configuration xmlPropConfig(String path) {
        if (StringAssist.isBlank(path)) {
            throw new RuntimeException("no config path");
        }
        try {
            Properties properties = new Properties();

            InputStream in = ClassPathResourceAssist.locateInputStream(path);
            if (in == null) {
                return null;
            }

            properties.loadFromXML(in);

            Configuration configuration = new PropertiesConfiguration();

            for (Entry entry : properties.entrySet()) {
                if (entry.getKey() != null) {
                    String key = String.valueOf(entry.getKey());
                    Object value = entry.getValue();
                    configuration.setProperty(key, value);
                }
            }

            return configuration;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * XML文档的字符编码规定为UTF-8
     */
    public static Document xmlDocumentConfig(String path) {
        if (StringAssist.isBlank(path)) {
            throw new RuntimeException("no config path");
        }

        try {
            Configurations configs = new Configurations();
            XMLConfiguration configuration = configs.xml(path);

            if (configuration == null) {
                return null;
            }

            Document document = configuration.getDocument();

            if (document != null) {
                document.normalizeDocument();
            }

            return document;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static Configuration iniPropConfig(String path) {
        if (StringAssist.isBlank(path)) {
            throw new RuntimeException("no config path");
        }
        try {
            Configurations configs = new Configurations();
            return configs.ini(path);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static Configuration iniPropConfig(String path, String encoding) {
        if (StringAssist.isBlank(path)) {
            throw new RuntimeException("no config path");
        }
        try {
            Parameters parameters = new Parameters();
            FileBasedBuilderParameters paras = parameters.fileBased().setEncoding(encoding).setFileName(path);

            FileBasedConfigurationBuilder builder = new FileBasedConfigurationBuilder(INIConfiguration.class);
            builder.configure(paras);

            return (Configuration)builder.getConfiguration();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
}
