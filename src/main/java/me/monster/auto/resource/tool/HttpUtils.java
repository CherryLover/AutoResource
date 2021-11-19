package me.monster.auto.resource.tool;

import okhttp3.*;

import java.io.IOException;
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

    public static String sendGetRequest(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        final Response response = client.newCall(request).execute();
        printHttpInfo(request, response);
        return tryGetResponse(response);
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
        printHttpInfo(request, response);
        return tryGetResponse(response);
    }

    private static String tryGetResponse(Response response) throws IOException {
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

    private static void printHttpInfo(Request request, Response response) {
        System.out.println(request.method() + " -> " + request.url().toString() + " with " + response.code() + " " + response.message());
    }

}
