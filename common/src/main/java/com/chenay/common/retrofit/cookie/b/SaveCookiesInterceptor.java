package com.chenay.common.retrofit.cookie.b;

import com.chenay.common.storage.PreferenceUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Y.Chen5 on 5/2/2018.
 */

public class SaveCookiesInterceptor implements Interceptor {
     static final String PREF_COOKIES = "PREF_COOKIES";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            PreferenceUtil.newInstance().commitStringSet(PREF_COOKIES, cookies);
        }

        return originalResponse;
    }

}
