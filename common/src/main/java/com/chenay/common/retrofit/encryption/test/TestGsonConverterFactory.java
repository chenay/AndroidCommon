package com.chenay.common.retrofit.encryption.test;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import com.chenay.common.http.HttpHeaders;
import com.chenay.common.http.MultiValueMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Created by Y.Chen5 on 5/2/2018.
 */

public class TestGsonConverterFactory extends Converter.Factory {
    private static final String TAG = "TestGsonConverterFactory";

    static class HeadTypeAdapter extends TypeAdapter<HttpHeaders> {

        @Override
        public void write(JsonWriter out, HttpHeaders value) throws IOException {

        }

        @Override
        public HttpHeaders read(JsonReader in) throws IOException {
            return new HttpHeaders();
        }
    }


    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static TestGsonConverterFactory create() {

 //       final GsonBuilder gsonBuilder = new GsonBuilder();
//                .registerTypeAdapter(HttpHeaders.class, new HeadTypeAdapter())
//                .registerTypeHierarchyAdapter(MultiValueMapTypeAdapter.class, new MultiValueMapTypeAdapter());
   //     final Gson gson = gsonBuilder.create();
        Gson gson = new Gson();
        return create(gson);
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static TestGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new TestGsonConverterFactory(gson);
    }

    private final Gson gson;

    private TestGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @SuppressLint("LongLogTag")
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = null;
        try {
//            adapter = gson.getAdapter(TypeToken.get(type));
            final TypeToken<?> type1 = TypeToken.get(type);
            adapter = gson.getAdapter(type1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Log.d(TAG, "responseBodyConverter: " + type1.getType().getTypeName());
            }
            return new TestGsonResponseBodyConverter<>(gson, adapter);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new TestGsonRequestBodyConverter<>(gson, adapter);
    }
}
