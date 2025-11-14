package me.dslztx.io.socket.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientBuilderAssist {

    public static CloseableHttpClient buildConnectionReuseHttpClient(int maxTotalInConnectionPool,
        int maxPerRouteInConnectionPool, int connectionRequestTimeout, int connectTimeout, int socketTimeout) {

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(maxTotalInConnectionPool);
        connManager.setDefaultMaxPerRoute(maxPerRouteInConnectionPool);

        // 默认的请求参数配置，具体请求时可以传入覆盖配置
        RequestConfig defaultRequestConfig =
            RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();

        return HttpClients.custom().setConnectionReuseStrategy(new DefaultConnectionReuseStrategy())
            .setConnectionManager(connManager).setDefaultRequestConfig(defaultRequestConfig).build();
    }

    public static CloseableHttpClient buildNoConnectionReuseHttpClient(int connectionRequestTimeout, int connectTimeout,
        int socketTimeout) {

        // 默认的请求参数配置，具体请求时可以传入覆盖配置
        RequestConfig defaultRequestConfig =
            RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();

        return HttpClients.custom().setConnectionReuseStrategy(new NoConnectionReuseStrategy())
            .setDefaultRequestConfig(defaultRequestConfig).build();
    }

}
