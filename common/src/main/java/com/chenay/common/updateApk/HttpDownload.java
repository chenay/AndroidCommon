package com.chenay.common.updateApk;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author Chenay
 * @date 2016/5/20
 * 类名：HttpDownload<BR>
 * 作用：下载文件的类<BR>
 */
public class HttpDownload {
    public final static int DOWN_FILE = 0;
    public final static int DOWN_SUCESS = 1;


    public static int downLoadFile(String httpUrl, String fileName, String path) {

        try {
            URL url = new URL(httpUrl);
            // 当存放文件的文件目录不存在的时候创建文件目录
            File tmpFile = new File(path);
            if (!tmpFile.exists()) {
                if (!tmpFile.mkdirs()) {
                    Log.e("downLoadFile", "downLoadFile: 文件夹创建失败!:" + path);
                }
            }

            Boolean b = false;
            boolean useHttps = httpUrl.startsWith("https");
            if (useHttps) {
                b = downLoadFileHttps(url, fileName, path);
            } else {
                b = downLoadFileHttp(url, fileName, path);
            }

            if (b) {
                return DOWN_SUCESS;
            } else {
                return DOWN_FILE;
            }
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            return DOWN_FILE;
        }
    }

    private static boolean downLoadFileHttp(URL url, String fileName, String path) throws IOException, IllegalStateException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();


        conn.setRequestMethod("POST");
        OutputStream os = conn.getOutputStream();
        os.write(fileName.getBytes("utf-8"));
        os.flush();
        os.close();

        conn.connect();// http请求服务器
        // http请求取得响应的时候
        final int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            File file = new File(path + "/" + fileName);
            InputStream is = conn.getInputStream();// 获得http请求返回的InputStream对象。
            FileOutputStream fos = new FileOutputStream(file);// 获得文件输出流对象来写文件用的
            byte[] buf = new byte[1024];
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
            fos.close();
            if (is != null) {
                is.close();
            }
            conn.disconnect();

            return true;
        } else {
            conn.disconnect();
            System.out.println("nono");
            throw new IllegalStateException("nono http返回值 is not 200");
        }

    }

    private static Boolean downLoadFileHttps(URL url, String fileName, String path) throws IOException, IllegalStateException {


        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
        HttpsURLConnection conn = (HttpsURLConnection) conn1;
        trustAllHosts(conn);
        conn.getHostnameVerifier();
        conn.setHostnameVerifier(DO_NOT_VERIFY);

        conn.setRequestMethod("POST");
        OutputStream os = conn.getOutputStream();
        os.write(fileName.getBytes("utf-8"));
        os.flush();
        os.close();

        conn.connect();// http请求服务器
        // http请求取得响应的时候
        if (conn.getResponseCode() == 200) {
            File file = new File(path + "/" + fileName);
            InputStream is = conn.getInputStream();// 获得http请求返回的InputStream对象。
            FileOutputStream fos = new FileOutputStream(file);// 获得文件输出流对象来写文件用的
            byte[] buf = new byte[1024];
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
            fos.close();
            if (is != null) {
                is.close();
            }
            conn.disconnect();

            return true;
        } else {
            conn.disconnect();
            System.out.println("nono");
            throw new IllegalStateException("nono http返回值 is not 200");
        }
    }


    /**
     * 覆盖java默认的证书验证
     */
    public static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }

        @Override
        @SuppressLint("TrustAllX509TrustManager")
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        @SuppressLint("TrustAllX509TrustManager")
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
    }};

    /**
     * 设置不验证主机
     */
    public static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        @SuppressLint("BadHostnameVerifier")
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * 信任所有
     *
     * @param connection
     * @return
     */
    public static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
        SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory newFactory = sc.getSocketFactory();
            connection.setSSLSocketFactory(newFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldFactory;
    }
}