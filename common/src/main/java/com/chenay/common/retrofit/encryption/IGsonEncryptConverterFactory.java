package com.chenay.common.retrofit.encryption;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.IGsonConverterFactory;

/**
 * Created by Y.Chen5 on 5/2/2018.
 */

public class IGsonEncryptConverterFactory extends IGsonConverterFactory {
    protected IGsonEncryptConverterFactory(Gson gson) {
        super(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        final TypeToken<?> type1 = TypeToken.get(type);
        TypeAdapter<?> adapter = gson.getAdapter(type1);

        return new IGsonResponseBodyConverter<>(gson, adapter, type1);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new IGsonRequestBodyConverter<>(gson, adapter);
    }
}
