package me.dslztx.assist.client.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import me.dslztx.assist.util.StringAssist;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlClientFactory {

  private static final Logger logger = LoggerFactory.getLogger(MysqlClientFactory.class);

  private static final String CONFIG_FILE = "mysql.properties";

  private static volatile boolean init = false;

  private static Map<String, DruidDataSource> groupedDataSource = new HashMap<String, DruidDataSource>();

  public static DataSource obtainDataSource(String group) {
    if (!init) {
      init();
    }

    return groupedDataSource.get(group);
  }

  private static void init() {
    if (!init) {
      synchronized (MysqlClientFactory.class) {
        if (!init) {
          try {
            Configurations configs = new Configurations();

            Configuration configuration = configs.properties(new File(CONFIG_FILE));

            String groups = configuration.getString("groups");
            if (StringAssist.isBlank(groups)) {
              throw new RuntimeException("no groups");
            }

            String[] groupArray = groups.split(",");
            for (String group : groupArray) {
              logger.info("generate druiddatasource for group: " + group);

              Configuration subConfiguration = configuration.subset(group);

              groupedDataSource.put(group, generateDruidDataSource(subConfiguration));
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

    if (StringAssist.isBlank(url) || StringAssist.isBlank(username) || StringAssist
        .isBlank(password)) {
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
