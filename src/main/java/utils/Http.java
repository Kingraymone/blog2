package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Http {
    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doGet(String url, Map<String, String> headers) {
        StringBuffer buffer = doGetBuffer(url, headers);
        return buffer == null ? null : buffer.toString();
    }

    public static StringBuffer doGetBuffer(String url, Map<String, String> headers) {
        HttpURLConnection http = null;
        BufferedReader reader =null;
        try {
            /*获得HTTP*/
            URL getUrl = new URL(url);
            http = (HttpURLConnection) getUrl.openConnection();
            /*参数设置*/
            // 设置是否向HttpURLConnection输出
            http.setDoOutput(false);
            // 设置是否从httpUrlConnection读入
            http.setDoInput(true);
            // 设置请求方式　默认为GET
            http.setRequestMethod("GET");
            // 设置是否使用缓存
            http.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            http.setInstanceFollowRedirects(true);
            // 设置超时时间
            http.setConnectTimeout(3000);
            // 设置请求头
            if (headers != null) {
                headers.forEach(http::setRequestProperty);
            }
            // 连接，握手
            http.connect();
            // 获得响应结果
            int code = http.getResponseCode();
            StringBuffer result = new StringBuffer();
            // 正常响应
            if (code == 200) {
                // 获得响应文本内容编码
                String headerField = http.getHeaderField("Content-Type");
                String charSet = "utf-8";
                int pos = 0;
                if ((pos = headerField.lastIndexOf("charset")) != -1) {
                    charSet = headerField.substring(pos + 8);
                }
                // 从流中读取响应信息
                reader = new BufferedReader(new InputStreamReader(http.getInputStream(), charSet));
                String line = null;
                // 循环从流中读取
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (http != null) {
                http.disconnect();
            }
        }
    }
}
