package com.chenay.common.retrofit.cookie.c;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 将Http返回的cookie存储到本地
 * Created by Y.Chen5 on 5/2/2018.
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        if (chain == null)
//            Log.d("http", "Receivedchain == null");
//        Response originalResponse = chain.proceed(chain.request());
//        Log.d("http", "originalResponse" + originalResponse.toString());
//        if (!originalResponse.headers("set-cookie").isEmpty()) {
//            final StringBuffer cookieBuffer = new StringBuffer();
//            Observable.from(originalResponse.headers("set-cookie"))
//                    .map(new Func1<String, String>() {
//                        @Override
//                        public String call(String s) {
//                            String[] cookieArray = s.split(";");
//                            return cookieArray[0];
//                        }
//                    })
//                    .subscribe(new Action1<String>() {
//                        @Override
//                        public void call(String cookie) {
//                            cookieBuffer.append(cookie).append(";");
//                        }
//                    });
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("cookie", cookieBuffer.toString());
//            Log.d("http", "ReceivedCookiesInterceptor" + cookieBuffer.toString());
//            editor.commit();
//        }
//
//        return originalResponse;
        return null;
    }
}

