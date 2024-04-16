package me.dslztx.io.socket.http;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.CloseableAssist;

@Slf4j
public class HttpClientAssist {

    private static final String CONTENT_TYPE_JSON = "application/json";

    public static String httpPostJSONUTF8(String url, String jsonRequest) {
        return httpPostJSON(url, null, jsonRequest, Consts.UTF_8);
    }

    public static String httpPostJSONUTF8WithHeader(String url, Map<String, String> headerMap, String jsonRequest) {
        return httpPostJSON(url, headerMap, jsonRequest, Consts.UTF_8);
    }

    public static String httpPostJSON(String url, Map<String, String> headerMap, String jsonRequest, Charset encoding) {
        String resp = "";

        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();

            httpPost = new HttpPost(url);

            if (Objects.nonNull(headerMap) && headerMap.size() > 0) {
                for (String key : headerMap.keySet()) {
                    httpPost.addHeader(key, headerMap.get(key));
                }
            }

            StringEntity paramEntity = new StringEntity(jsonRequest, encoding);
            paramEntity.setContentType(CONTENT_TYPE_JSON);
            httpPost.setEntity(paramEntity);

            response = httpClient.execute(httpPost);

            if (Objects.nonNull(response)) {
                resp = EntityUtils.toString(response.getEntity(), encoding);
            }
        } catch (Exception e) {
            log.error(jsonRequest, e);
        } finally {
            CloseableAssist.closeQuietly(response);

            if (Objects.nonNull(httpPost)) {
                httpPost.releaseConnection();
            }

            CloseableAssist.closeQuietly(httpClient);
        }

        return resp;
    }

    public static String httpPostCommon(String url, Map<String, String> headerMap, Map<String, Object> params,
        Charset encoding) {

        String resp = "";

        CloseableHttpClient httpClient = null;

        HttpPost httpPost = null;

        CloseableHttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();

            httpPost = new HttpPost(url);

            if (Objects.nonNull(headerMap) && headerMap.size() > 0) {
                for (String key : headerMap.keySet()) {
                    httpPost.addHeader(key, headerMap.get(key));
                }
            }

            if (Objects.nonNull(params) && params.size() > 0) {
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() instanceof String[]) {
                        for (String value : (String[])entry.getValue()) {
                            formParams.add(new BasicNameValuePair(entry.getKey(), value));
                        }
                    } else {
                        formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                }
                UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formParams, encoding);
                httpPost.setEntity(postEntity);
            }

            response = httpClient.execute(httpPost);

            if (Objects.nonNull(response)) {
                resp = EntityUtils.toString(response.getEntity(), encoding);
            }

        } catch (Exception e) {
            log.error("", e);
        } finally {
            CloseableAssist.closeQuietly(response);

            if (Objects.nonNull(httpPost)) {
                httpPost.releaseConnection();
            }

            CloseableAssist.closeQuietly(httpClient);
        }
        return resp;
    }

    public static String httpPostByteArray(String url, Map<String, String> headerMap, byte[] data, Charset encoding) {

        String resp = "";

        CloseableHttpClient httpClient = null;

        HttpPost httpPost = null;

        CloseableHttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();

            httpPost = new HttpPost(url);

            if (Objects.nonNull(headerMap) && headerMap.size() > 0) {
                for (String key : headerMap.keySet()) {
                    httpPost.addHeader(key, headerMap.get(key));
                }
            }

            if (Objects.nonNull(data)) {
                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(data);

                httpPost.setEntity(byteArrayEntity);
            }

            response = httpClient.execute(httpPost);

            if (Objects.nonNull(response)) {
                resp = EntityUtils.toString(response.getEntity(), encoding);
            }

        } catch (Exception e) {
            log.error("", e);
        } finally {
            CloseableAssist.closeQuietly(response);

            if (Objects.nonNull(httpPost)) {
                httpPost.releaseConnection();
            }

            CloseableAssist.closeQuietly(httpClient);
        }
        return resp;
    }

    public static String httpGetUTF8(String url) {
        return httpGet(url, Consts.UTF_8);
    }

    public static String httpGet(String url, Charset encoding) {
        String resp = null;

        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();

            httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);

            if (Objects.nonNull(response)) {
                resp = EntityUtils.toString(response.getEntity(), encoding);
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            CloseableAssist.closeQuietly(response);

            if (Objects.nonNull(httpGet)) {
                httpGet.releaseConnection();
            }

            CloseableAssist.closeQuietly(httpClient);
        }

        return resp;
    }

}
