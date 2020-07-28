package com.chenay.common.retrofit.encryption;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.IGsonConverterFactory;

/**
 * Created by Y.Chen5 on 5/2/2018.
 */

public class IGsonEncryptConverterFactory extends Converter.Factory {
    public static IGsonEncryptConverterFactory iGsonConverterFactory;
    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static IGsonEncryptConverterFactory create() {
        if (iGsonConverterFactory == null) {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Gson gson = gsonBuilder
                        .registerTypeAdapter(LocalDateTime.class, new IGsonConverterFactory.LocalDateAdapter()).create();

                registerMore(gsonBuilder);
                iGsonConverterFactory = create(gson);
            } else {
                registerMore(gsonBuilder);
                iGsonConverterFactory = create(gsonBuilder.create());
            }

        }
        return iGsonConverterFactory;
    }

    public static void registerMore(GsonBuilder gsonBuilder) {

    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static IGsonEncryptConverterFactory create(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        return new IGsonEncryptConverterFactory(gson);
    }

    private final Gson gson;

    private IGsonEncryptConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        final TypeToken<?> type1 = TypeToken.get(type);
        TypeAdapter<?> adapter = gson.getAdapter(type1);

        return new IGsonResponseBodyConverter<>(gson, adapter,type1);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new IGsonRequestBodyConverter<>(gson, adapter);
    }
}
