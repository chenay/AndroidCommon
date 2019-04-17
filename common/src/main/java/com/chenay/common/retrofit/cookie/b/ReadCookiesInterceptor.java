package com.chenay.common.retrofit.cookie.b;

import android.util.Log;

import com.chenay.common.storage.PreferenceUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.chenay.common.retrofit.cookie.b.SaveCookiesInterceptor.PREF_COOKIES;

/**
 * Created by Y.Chen5 on 5/2/2018.
 */

public class ReadCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet preferences = (HashSet) PreferenceUtil.newInstance().getStringSet(PREF_COOKIES, new HashSet<String>());
        for (Object cookie : preferences) {
            builder.addHeader("Cookie", cookie.toString());
            // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            Log.v("OkHttp", "Adding Header: " + cookie);
        }

        return chain.proceed(builder.build());
    }


}
