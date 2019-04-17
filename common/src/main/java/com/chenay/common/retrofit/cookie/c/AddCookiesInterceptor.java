package com.chenay.common.retrofit.cookie.c;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 用来将本地的cookie追加到http请求头中；
 * Created by Y.Chen5 on 5/2/2018.
 */

public class AddCookiesInterceptor implements Interceptor {
    private Context context;
    private String lang;

    public AddCookiesInterceptor(Context context, String lang) {
        super();
        this.context = context;
        this.lang = lang;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        if (chain == null)
//            Log.d("http", "Addchain == null");
//        final Request.Builder builder = chain.request().newBuilder();
//        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
//        Observable.just(sharedPreferences.getString("cookie", ""))
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String cookie) {
//                        if (cookie.contains("lang=ch")) {
//                            cookie = cookie.replace("lang=ch", "lang=" + lang);
//                        }
//                        if (cookie.contains("lang=en")) {
//                            cookie = cookie.replace("lang=en", "lang=" + lang);
//                        }
//                        //添加cookie
////                        Log.d("http", "AddCookiesInterceptor"+cookie);
//                        builder.addHeader("cookie", cookie);
//                    }
//                });
//        return chain.proceed(builder.build());
        return null;
    }
}
