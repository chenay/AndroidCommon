package com.chenay.common.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chenay.common.retrofit.cookie.b.ReadCookiesInterceptor;
import com.chenay.common.retrofit.cookie.b.SaveCookiesInterceptor;
import com.chenay.common.retrofit.encryption.IGsonConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.io.InputStream;
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

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 1
 *
 * @author Y.Chen5
 * @date 5/2/2018
 */

public class RetrofitUtils {

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(30, TimeUnit.SECONDS).
            writeTimeout(30, TimeUnit.SECONDS)
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory()) //忽略证书
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//
            .build();

    @NonNull
    public static Retrofit.Builder getRetrofitBulie(@NonNull String head, @NonNull String serverIP, @NonNull String serverPort) {


        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(head + serverIP + ":" + serverPort)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(IGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder;

    }

    @NonNull
    public static Retrofit getRetrofit(@NonNull String serverIP, @NonNull String serverPort, InputStream[] inputStream) {
        //校验证书
//        setCertificates(clientBuilder, inputStream);
        Retrofit build = new Retrofit.Builder()
                .baseUrl(serverIP + ":" + serverPort)
                .client(client)
//                    .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(IGsonConverterFactory.create()) //数据加密
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return build;
    }

    @NonNull
    public static Retrofit getRetrofitNoEncrypt(@NonNull String serverIP, @NonNull String serverPort, InputStream[] inputStream) {
        //校验证书
//        setCertificates(clientBuilder, inputStream);
        Retrofit build = new Retrofit.Builder()
                .baseUrl(serverIP + ":" + serverPort)
                .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(IGsonConverterFactory.create()) //数据加密
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return build;
    }

    public static SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

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
    public static void setCertificates(OkHttpClient.Builder clientBuilder, InputStream... certificates) {
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

    public static Retrofit getRetrofit_b(@NonNull String head, @NonNull String serverIP, @NonNull String serverPort) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new ReadCookiesInterceptor());
        okHttpClient.interceptors().add(new SaveCookiesInterceptor());

        return new Retrofit.Builder()
                .baseUrl(head + serverIP + ":" + serverPort)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static Retrofit getRetrofit1(@NonNull String head, @NonNull String serverIP, @NonNull String serverPort) {
//        SLSocketFactory sslSocketFactory =getSSLSocketFactory_Certificate(context,"BKS", R.raw.XXX);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .certificatePinner(new CertificatePinner.Builder()
                        .add("YOU API.com", "sha1/DmxUShsZuNiqPQsX2Oi9uv2sCnw=")
                        .add("YOU API..com", "sha1/SXxoaOSEzPC6BgGmxAt/EAcsajw=")
                        .add("YOU API..com", "sha1/blhOM3W9V/bVQhsWAcLYwPU6n24=")
                        .add("YOU API..com", "sha1/T5x9IXmcrQ7YuQxXnxoCmeeQ84c=")
                        .build());
        return new Retrofit.Builder()
                .baseUrl(head + serverIP + ":" + serverPort)
                .addConverterFactory(GsonConverterFactory.create())
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
}
