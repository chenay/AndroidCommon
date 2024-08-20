package com.chenay.common.retrofit;

import android.content.Context;


import com.chenay.common.retrofit.cookie.b.ReadCookiesInterceptor;
import com.chenay.common.retrofit.cookie.b.SaveCookiesInterceptor;
import com.chenay.common.retrofit.encryption.IGsonEncryptConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.annotations.NonNull;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.IGsonConverterFactory;


/**
 * 1
 *
 * @author Y.Chen5
 * @date 5/2/2018
 */

public class RetrofitUtils {

    private OkHttpClient.Builder clientBuilder;
    private Retrofit.Builder builder;

    public void setClientBuilder(OkHttpClient.Builder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

    public OkHttpClient.Builder getClientBuilder() {
        return clientBuilder;
    }

    @NonNull
    public Retrofit.Builder getBuilder() {
        return builder;
    }

    public void setBuilder(Retrofit.Builder builder) {
        this.builder = builder;
    }

    public RetrofitUtils() {
    }

    public void initClient() {
        if (clientBuilder == null) {
            clientBuilder = new OkHttpClient.Builder().
                    connectTimeout(60, TimeUnit.SECONDS).
                    readTimeout(30, TimeUnit.SECONDS).
                    writeTimeout(30, TimeUnit.SECONDS);

        }
    }

    public void ignoreCertificate(OkHttpClient.Builder clientBuilder) {
        //忽略证书
        clientBuilder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
        clientBuilder .hostnameVerifier(SSLSocketClient.getHostnameVerifier());

    }


    @NonNull
    private Retrofit.Builder getRetrofitBulie(@NonNull String serverIP, @NonNull String serverPort) {
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(serverIP + ":" + serverPort);
        this.builder = builder;
        return builder;

    }

    @NonNull
    public Retrofit getRetrofit(@NonNull String serverIP, @NonNull String serverPort, InputStream[] inputStream) {
        initClient();
        if (inputStream != null && inputStream.length > 0) {
            //校验证书
            setCertificates(clientBuilder, inputStream);
        }
        return getRetrofitBulie(serverIP, serverPort)
                .client(clientBuilder.build())
                //数据加密
                .addConverterFactory(IGsonEncryptConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    public Retrofit getRetrofitNoEncrypt(@NonNull String serverIP, @NonNull String serverPort, InputStream[] inputStream) {
        initClient();
        if (inputStream != null && inputStream.length > 0) {
            //校验证书
            setCertificates(clientBuilder, inputStream);
        }
        return getRetrofitBulie(serverIP, serverPort)
                .client(clientBuilder.build())
                .addConverterFactory(IGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                InputStream certificate = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));

                if (certificate != null) {
                    certificate.close();
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过okhttpClient来设置证书
     *
     * @param clientBuilder OKhttpClient.builder
     * @param certificates  读取证书的InputStream
     */
    public void setCertificates(OkHttpClient.Builder clientBuilder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory
                        .generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static Retrofit getRetrofit_a(@NonNull String head, @NonNull String serverIP, @NonNull String serverPort) {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(
//                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//                .cookieJar(new CookieManger(context))
//                .addInterceptor(loginInterceptor)
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .build();
//
//
//        return new Retrofit.Builder()
//                .baseUrl(head + serverIP + ":" + serverPort)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }

    public Retrofit getRetrofit_b(@NonNull String serverIP, @NonNull String serverPort) {
        initClient();
        clientBuilder.interceptors().add(new ReadCookiesInterceptor());
        clientBuilder.interceptors().add(new SaveCookiesInterceptor());

        return getRetrofitBulie(serverIP, serverPort)
                .addConverterFactory(IGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build();
    }

    public Retrofit getRetrofit1(@NonNull String serverIP, @NonNull String serverPort) {
//        SLSocketFactory sslSocketFactory =getSSLSocketFactory_Certificate(context,"BKS", R.raw.XXX);
        initClient();
        clientBuilder.certificatePinner(new CertificatePinner.Builder()
                .add("YOU API.com", "sha1/DmxUShsZuNiqPQsX2Oi9uv2sCnw=")
                .add("YOU API..com", "sha1/SXxoaOSEzPC6BgGmxAt/EAcsajw=")
                .add("YOU API..com", "sha1/blhOM3W9V/bVQhsWAcLYwPU6n24=")
                .add("YOU API..com", "sha1/T5x9IXmcrQ7YuQxXnxoCmeeQ84c=")
                .build());
        return getRetrofitBulie(serverIP, serverPort)
                .addConverterFactory(IGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                //开启请求头
//                .client(new OkHttpClient().newBuilder().addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)))
//                //开启Body日志
//                .client(new OkHttpClient().newBuilder().addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)))
//                //基础输出
//                .client(new OkHttpClient().newBuilder().addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)))
//                //添加头部信息
//                // @Headers({ "Accept: application/vnd.github.v3.full+json", "User-Agent: Retrofit-your-App"})
//                //@get("users/{username}")
//                //Call<User>   getUser(@Path("username") String username);
//                .client(new OkHttpClient.Builder()
//                        .addInterceptor(new Interceptor() {
//                            @Override
//                            public Response intercept(Chain chain) throws IOException {
//                                Request request = chain.request()
//                                        .newBuilder()
//                                        .addHeader("mac", "f8:00:ea:10:45")
//                                        .addHeader("uuid", "gdeflatfgfg5454545e")
//                                        .addHeader("userId", "Fea2405144")
//                                        .addHeader("netWork", "wifi")
//                                        .build();
//                                return chain.proceed(request);
//                            }
//                        }))
                .build();

    }


    /**
     * 获取单例内部类句柄
     *
     * @return Created by Chenay
     */
    private static class RetrofitUtilsHolder {
        private static final RetrofitUtils SINGLETON = new RetrofitUtils();
    }

    /**
     * 获取单例
     *
     * @return Created by Chenay
     */
    public static RetrofitUtils newInstance() {
        return RetrofitUtilsHolder.SINGLETON;
    }

    /**
     * 防止反序列化操作破坏单例模式 (条件) implements Serializable
     *
     * @return Created by Chenay
     */
    private Object readResolve() throws ObjectStreamException {
        return RetrofitUtilsHolder.SINGLETON;
    }
}
