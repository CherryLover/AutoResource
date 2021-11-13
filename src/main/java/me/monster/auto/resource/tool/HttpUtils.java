package me.monster.auto.resource.tool;

import okhttp3.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * <p>
 * 网络请求操作工具类
 *
 * @author niujinpeng
 * @Date 2021/02/07 21:25
 * @link https://github.com/niumoo
 */
public class HttpUtils {

    private final static OkHttpClient client = new OkHttpClient.Builder().build();

    /**
     * 获取 HTTP 连接
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getGetConnection(String url) throws IOException {
        URL httpUrl = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection)httpUrl.openConnection();
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
        return httpConnection;
    }

    public static HttpURLConnection getPostConnection(String url, String method, Map<String, String> params) throws IOException {
        URL httpUrl = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) httpUrl.openConnection();
        httpConnection.setRequestMethod(method);
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
        return httpConnection;
    }

    /**
     * 请求指定 URL 返回内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getHttpContent(String url) throws IOException {
        return getGetResponse(url);
    }

    private static String getGetResponse(String url) throws IOException {
        HttpURLConnection httpUrlConnection = getGetConnection(url);
        StringBuilder stringBuilder = new StringBuilder();
        // 获得输入流
        try (InputStream input = httpUrlConnection.getInputStream(); BufferedInputStream bis = new BufferedInputStream(
            input);) {
            byte[] buffer = new byte[1024];
            int len = -1;
            // 读到文件末尾则返回-1
            while ((len = bis.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpUrlConnection.disconnect();
        }
        return stringBuilder.toString();
    }

    public static String getPostResponse(String url, Map<String, String> params) throws IOException {
        final FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (String s : params.keySet()) {
            bodyBuilder.add(s, params.get(s));
        }
        final Request request = new Request.Builder()
                .url(url)
                .post(bodyBuilder.build())
                .build();
        final Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            final ResponseBody body = response.body();
            if (body == null) {
                return "";
            } else {
                return body.string();
            }
        } else {
            return "";
        }
    }

}
