package com.sxpcwlkj.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpRequestUtil {

    public static void main(String[] args) {

        String url = "http://zz-api.xuandan.com/DataApi/index?appkey=a3chc2dttt&key=韩版";
        System.out.println("===>" + GetHttpRequest(url));

    }

    /**
     * 有参数 Get 请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String Get(String url, Map<String, Object> params) throws Exception {
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        StringBuffer buf = new StringBuffer();
        buf.append(url);
        buf.append("?");
        for (int i = 0; i < pairs.size(); i++) {
            NameValuePair nameValuePair = pairs.get(i);
            if (i == 0) {
                buf.append(nameValuePair.getName() + "=" + nameValuePair.getValue());
            } else {
                buf.append("&" + nameValuePair.getName() + "=" + nameValuePair.getValue());
            }
        }
        return GetHttpRequest(buf.toString(), null);
    }

    /**
     * get请求 带参数 带请求头
     *
     * @param url
     * @param params
     * @param token
     * @return
     * @throws Exception
     */
    public static String Get(String url, Map<String, Object> params, String token) throws Exception {
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        StringBuffer buf = new StringBuffer();
        buf.append(url);
        buf.append("?");
        for (int i = 0; i < pairs.size(); i++) {
            NameValuePair nameValuePair = pairs.get(i);
            if (i == 0) {
                buf.append(nameValuePair.getName() + "=" + nameValuePair.getValue());
            } else {
                buf.append("&" + nameValuePair.getName() + "=" + nameValuePair.getValue());
            }
        }
        return GetHttpRequest(buf.toString(), token);
    }

    public static String GetHttpRequest(String url, String token) {
        //System.out.println(url);
        HttpClient client = HttpClients.createDefault();
        url = DataUtil.deleteString(url);
        HttpGet get = new HttpGet(url);
        try {
            if (token != null) {
                /*
                 * 添加请求头信息
                 */
                // 浏览器表示
                get.addHeader("token", token);
            }
            // 传输的类型
            get.addHeader("Content-Type", "application/x-www-form-urlencoded");
            // 执行请求
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetHttpRequest(String url) {
        //System.out.println(url);
        HttpClient client = HttpClients.createDefault();
        url = DataUtil.deleteString(url);
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 有参数 Post 请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String Post(String url, Map<String, Object> params) throws Exception {
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        return PostHttpRequest(url, pairs);
    }

    /**
     * 向指定URL发送GET方法的请求
     * https://blog.csdn.net/HezhezhiyuLe/article/details/92395041?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param
     */
    public static String sendGet(String url, String param, Map<String, String> header) throws UnsupportedEncodingException, IOException {
        String result = "";
        BufferedReader in = null;
        String urlNameString = url + "?" + param;
        URL realUrl = new URL(urlNameString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        //设置超时时间
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(15000);
        // 设置通用的请求属性
        if (header != null) {
            Iterator<Map.Entry<String, String>> it = header.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                System.out.println(entry.getKey() + ":" + entry.getValue());
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> map = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : map.keySet()) {
            System.out.println(key + "--->" + map.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应，设置utf8防止中文乱码
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (in != null) {
            in.close();
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     */
    public static String sendPost(String url, String param, Map<String, String> header) throws UnsupportedEncodingException, IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        //设置超时时间
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        // 设置通用的请求属性
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        return result;
    }


    /**
     * 上传执行
     *
     * @param file
     * @param params
     * @return String
     */
    public static String getUploadFile(String postUrl, File file, Map<String, Object> params, String token) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(postUrl);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
        String result = null;
        HttpResponse response = null;

        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mEntityBuilder.addBinaryBody("file", file);
        // 设置其他参数
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            mEntityBuilder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
        }
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(mEntityBuilder.build());
        try {
            if (token != null) {
                /*
                 * 添加请求头信息
                 */
                // 浏览器表示
                httpPost.addHeader("token", token);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            //上传成功，返回结果
            if (statusCode == HttpStatus.SC_OK) {
                log.info("名称为：{} 的文件上传成功", file.getName());
                HttpEntity resEntity = response.getEntity();
                result = EntityUtils.toString(resEntity);
                // 消耗掉response
                EntityUtils.consume(resEntity);
            } else {
                log.info("名称为：{} 的文件上传失败", file.getName());
                log.info("上传失败原因为：{}", EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            log.error("上传文件发生异常：{}", e);
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    //=================================  下面是封装方法 不值得一看 ==================================

    public static String PostHttpRequest(String Url, List<NameValuePair> params) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        //超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(300000)
                .setConnectTimeout(300000).build();
        String result = null;
        try {
            HttpPost request = new HttpPost(Url);
            request.setConfig(requestConfig);
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse respones = client.execute(request);
            if (respones.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(respones.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
        return result;
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        if (params == null || params.size() == 0) {
            return pairs;
        }
        for (Map.Entry<String, Object> param : params.entrySet()) {
            Object value = param.getValue();
            if (value instanceof String[]) {
                String[] values = (String[]) value;
                for (String v : values) {
                    pairs.add(new BasicNameValuePair(param.getKey(), v));
                }
            } else {
                if (value instanceof Integer) {
                    value = Integer.toString((Integer) value);
                } else if (value instanceof Long) {
                    value = Long.toString((Long) value);
                }
                pairs.add(new BasicNameValuePair(param.getKey(), (String) value));
            }
        }
        return pairs;
    }

}



