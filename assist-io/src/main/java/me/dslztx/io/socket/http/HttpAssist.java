package me.dslztx.io.socket.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.CloseableAssist;
import me.dslztx.io.domain.HttpResult;

@Slf4j
public class HttpAssist {

    private static CloseableHttpClient obtainHttpClient() {
        return HttpClients.createDefault();
    }

    private static CloseableHttpClient obtainHttpsClient() {
        try {
            // 配置，发送HTTPS请求时，忽略SSL证书认证

            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();

            return HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 不能直接返回HttpResponse，否则后续读取时会报错，因为这里已经关闭
     */
    private static HttpResult httpGet0(CloseableHttpClient httpClient, String url) {
        HttpGet httpPost = null;
        CloseableHttpResponse response = null;

        try {
            httpPost = new HttpGet(url);

            response = httpClient.execute(httpPost);

            return HttpResult.generateFrom(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            CloseableAssist.closeQuietly(response);

            if (Objects.nonNull(httpPost)) {
                httpPost.releaseConnection();
            }

            CloseableAssist.closeQuietly(httpClient);
        }
    }

    /**
     * 不能直接返回HttpResponse，否则后续读取时会报错，因为这里已经关闭
     */
    public static HttpResult httpGet(String url) {
        return httpGet0(obtainHttpClient(), url);
    }

    /**
     * 不能直接返回HttpResponse，否则后续读取时会报错，因为这里已经关闭
     */
    public static HttpResult httpsGet(String url) {
        return httpGet0(obtainHttpsClient(), url);
    }

    /**
     * 不能直接返回HttpResponse，否则后续读取时会报错，因为这里已经关闭
     */
    private static HttpResult httpPost0(CloseableHttpClient httpClient, String url, Map<String, String> headerMap,
        ContentType contentType, byte[] requestData) {

        HttpPost httpPost = null;
        CloseableHttpResponse response = null;

        try {
            if (requestData == null) {
                throw new RuntimeException("post data is null");
            }

            httpPost = new HttpPost(url);

            if (Objects.nonNull(headerMap) && headerMap.size() > 0) {
                for (String key : headerMap.keySet()) {
                    httpPost.addHeader(key, headerMap.get(key));
                }
            }

            // post请求数据都用ByteArrayEntity就够，无需再用StringEntity，FileEntity等
            // 结合contentType进行具体类型区分
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestData, contentType);

            httpPost.setEntity(byteArrayEntity);

            response = httpClient.execute(httpPost);

            return HttpResult.generateFrom(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            CloseableAssist.closeQuietly(response);

            if (Objects.nonNull(httpPost)) {
                httpPost.releaseConnection();
            }

            CloseableAssist.closeQuietly(httpClient);
        }

    }

    /**
     * 不能直接返回HttpResponse，否则后续读取时会报错，因为这里已经关闭
     */
    public static HttpResult httpPost(String url, Map<String, String> headerMap, ContentType contentType,
        byte[] requestData) {
        return httpPost0(obtainHttpClient(), url, headerMap, contentType, requestData);
    }

    /**
     * 不能直接返回HttpResponse，否则后续读取时会报错，因为这里已经关闭
     */
    public static HttpResult httpsPost(String url, Map<String, String> headerMap, ContentType contentType,
        byte[] requestData) {
        return httpPost0(obtainHttpsClient(), url, headerMap, contentType, requestData);
    }
}
