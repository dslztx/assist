package me.dslztx.assist.client.mysql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

import me.dslztx.assist.util.ConfigLoadAssist;
import me.dslztx.assist.util.StringAssist;

public class DataSourceFactory {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    private static final String CONFIG_FILE = "mysql.properties";

    private static volatile boolean init = false;

    private static Map<String, DruidDataSource> groupedDataSource = new HashMap<String, DruidDataSource>();

    public static DataSource obtainDataSource(String group) {
        if (!init) {
            init();
        }

        return groupedDataSource.get(group);
    }

    public static DataSource obtainDataSource() {
        if (!init) {
            init();
        }

        return groupedDataSource.get("default");
    }

    private static void init() {
        if (!init) {
            synchronized (DataSourceFactory.class) {
                if (!init) {
                    try {
                        Configuration configuration = ConfigLoadAssist.propConfig(CONFIG_FILE);

                        String groups = configuration.getString("groups");
                        if (StringAssist.isBlank(groups)) {
                            logger.info("generate druiddatasource for group: default");
                            groupedDataSource.put("default", generateDruidDataSource(configuration));
                        } else {
                            String[] groupArray = groups.split(",");
                            for (String group : groupArray) {
                                logger.info("generate druiddatasource for group: {}", group);

                                Configuration subConfiguration = configuration.subset(group);

                                groupedDataSource.put(group, generateDruidDataSource(subConfiguration));
                            }
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                        throw new RuntimeException(e);
                    } finally {
                        init = true;
                    }
                }
            }
        }
    }

    private static DruidDataSource generateDruidDataSource(Configuration configuration) {
        if (configuration == null) {
            return null;
        }

        String url = configuration.getString("url");
        String username = configuration.getString("username");
        String password = configuration.getString("password");

        if (StringAssist.isBlank(url) || StringAssist.isBlank(username) || StringAssist.isBlank(password)) {
            return null;
        }

        DruidDataSource dataSource = null;
        try {
            dataSource = new DruidDataSource();

            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            dataSource.init();
        } catch (SQLException e) {
            dataSource = null;
        } finally {
            return dataSource;
        }
    }
}
