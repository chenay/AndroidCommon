package com.chenay.common.utils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {
    private static final String TAG = "HttpUtils";

    public static String httpPost(String uri, String data) throws IOException {
        String result = null;
        HttpURLConnection conn = null;
        IOException ioException = null;
        try {
            URL url = new URL(uri);
            String content = data;
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60 * 1000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置请求的头
            conn.setRequestProperty("Connection", "keep-alive");
//                // 设置请求的头
            conn.setRequestProperty("Content-Type", "application/json");
//                // 设置请求的头
            conn.setRequestProperty("Content-Length",
                    String.valueOf(content.getBytes().length));
            conn.setDoOutput(true); // 发送POST请求必须设置允许输出
            conn.setDoInput(true); // 发送POST请求必须设置允许输入
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes("utf-8"));
            os.close();
            printResponseHeader(conn);
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                result = new String(baos.toByteArray());
            } else {
                ioException = new IOException("HTTP: " + conn.getResponseMessage());
            }
        } catch (IOException e) {
            ioException = new IOException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (ioException != null) {
                throw ioException;
            }
        }
        return result;
    }

    public static boolean httpGet(String uri, OutputStream outputStream) throws IOException {
        URL downUrl = new URL(uri);
        InputStream is = null;
        HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
        http.setConnectTimeout(5 * 1000);
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        http.setRequestProperty("Accept-Language", "zh-CN");
        http.setRequestProperty("Referer", downUrl.toString());
        http.setRequestProperty("Charset", "UTF-8");
        http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        http.setRequestProperty("Connection", "Keep-Alive");
        http.connect();
        int responseCode = http.getResponseCode();
        HttpUtils.printResponseHeader(http);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //获取连接的输入流，这个输入流就是图片的输入流
            is = http.getInputStream();
            //构建一个file对象用于存储图片
            int len = 0;
            byte[] buffer = new byte[1024];
            //将输入流写入到我们定义好的文件中
            while ((len = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            //将缓冲刷入文件
            outputStream.flush();
            is.close();
            http.disconnect();
            return true;
        } else {
            throw new IOException("HTTP: " + responseCode);
        }
    }

    public static String httpDown(String httpUrl, String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new NullPointerException(filePath + "文件创建失败");
            }
        }
        FileOutputStream fos = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        // 当存放文件的文件目录不存在的时候创建文件目录
            URL url = new URL(httpUrl);
            try {
                conn = (HttpURLConnection) url.openConnection();
                is = conn.getInputStream();// 获得http请求返回的InputStream对象。
                fos = new FileOutputStream(file);// 获得文件输出流对象来写文件用的
                byte[] buf = new byte[1024];
                conn.connect();// http请求服务器
                double count = 0;
                // http请求取得响应的时候
                if (conn.getResponseCode() == 200) {
                    while (true) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }
                        } else {
                            break;
                        }
                    }
                } else {
                    System.out.println("nono");
                    return null;
                }
                conn.disconnect();
                fos.close();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                    conn = null;
                }
                if (fos != null) {
                    try {
                        fos.close();
                        fos = null;
                    } catch (IOException e) {
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                        is = null;
                    } catch (IOException e) {
                    }
                }
            }
        return filePath;
    }

    /**
     * 上传方法
     * 返回上传完毕的文件名
     * *
     */
    public static boolean httpUpload(File file, String uri) {
        try {
            URL upDataPhoto = new URL(uri);
            HttpURLConnection http = (HttpURLConnection) upDataPhoto.openConnection();


            String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线

            //上传图片的一些参数设置
            http.setDoOutput(true);
            http.setUseCaches(true);
            http.setRequestProperty("Accept", "image/gif,   image/x-xbitmap,   image/jpeg,   image/pjpeg,   application/vnd.ms-excel,   application/vnd.ms-powerpoint,   application/msword,   application/x-shockwave-flash,   application/x-quickviewplus,   */*");
            http.setRequestProperty("Accept-Language", "zh-cn");
            http.setRequestMethod("POST");
            http.setRequestProperty("Accept-Encoding", "gzip,   deflate");
            http.setRequestProperty("User-Agent", "Mozilla/4.0   (compatible;   MSIE   6.0;   Windows   NT   5.1)");
            http.setRequestProperty("Connection", "Keep-Alive");
            http.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(http.getOutputStream());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
//            int leng = list.size();
//            for (int i = 0; i < leng; i++) {
//                String fname = list.get(i);
//                File file = new File(fname);
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file" + "\";filename=\"" + file.getName() + "\"\r\n");
//            sb.append("Content-Disposition: form-data;userId=\"" + IQClassGlobalManager.cloudUserInfo.studentId+ "\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:image/*" + "\r\n\r\n");

            byte[] data = sb.toString().getBytes();
            out.write(data);

//            StringBuilder sb1 = new StringBuilder();
//            sb1.append("--");
//            sb1.append(BOUNDARY);
//            sb1.append("\r\n");
//            sb1.append("Content-Disposition: form-data;name=\"student"  + "\";filename=\"" +IQClassGlobalManager.cloudUserInfo.studentId + "\"\r\n");
//            sb1.append("Content-Type:application/octet-stream" + "\r\n\r\n");
//            byte[] data1 = sb1.toString().getBytes();
//            out.write(data1);

            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个
            in.close();
//            }
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static AsyncHttpClient httpClientInstance() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        return asyncHttpClient;
    }

    public static AsyncHttpClient httpSyncPostWithJson(Context mContext, String jsonRequest, String url, ResponseHandlerInterface responseHandler) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        StringEntity entity = null;
        entity = new StringEntity(jsonRequest, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        asyncHttpClient.post(mContext, url, entity, "application/json", responseHandler);
        responseHandler.setTag(responseHandler);
        return asyncHttpClient;
    }


    public static void httpSyncPostUrl(AsyncHttpClient asyncHttpClient, Context mContext, String jsonRequest, String url, ResponseHandlerInterface responseHandler) {
        StringEntity entity = null;
        entity = new StringEntity(jsonRequest, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        asyncHttpClient.post(mContext, url, entity, "application/json", responseHandler);
        responseHandler.setTag(responseHandler);
    }

    /**
     * 取消下载
     *
     * @param asyncHttpClient
     */
    public static void httpSyncCancle(AsyncHttpClient asyncHttpClient, Object tag) {
        if (asyncHttpClient != null) {
            asyncHttpClient.cancelRequestsByTAG(tag, true);
            tag = null;
        }
    }

    public static void httpSyncCancleAll(AsyncHttpClient asyncHttpClient) {
        asyncHttpClient.cancelAllRequests(true);
        asyncHttpClient = null;
    }


    public static boolean checkIp(String ip) {
        boolean result = true;

        if (ip == null) {
            return false;
        }
        String ip1 = ip.replace('.', '#');
        String[] str = ip1.split("#");

        if (str.length != 4) {
            return false;
        }
        for (int i = 0; i < str.length; i++) {
            try {
                double num = Double.parseDouble(str[i]);
                if (num < 0 && num > 255) {
                    result = false;
                    break;
                }
            } catch (NumberFormatException e) {
                result = false;
                e.fillInStackTrace();
            }
        }
        return result;
    }

    /**
     * 打印Http头字段
     *
     * @param http
     */

    public static void printResponseHeader(HttpURLConnection http) {
        Map<String, String> header = getHttpResponseHeader(http);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
            print(key + entry.getValue());
        }
    }

    private static void print(String msg) {
        Log.i(TAG, msg);
    }


    /**
     * 获取Http响应头字段
     *
     * @param http
     * @return
     */
    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        for (int i = 0; ; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null) {
                break;
            }
            header.put(http.getHeaderFieldKey(i), mine);
        }
        return header;
    }
}
