package com.chenay.common.retrofit.encryption;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 1
 * Created by Y.Chen5 on 5/2/2018.
 */
public class IGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "IGsonResponseBodyConverter";
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final TypeToken<?> type;

    IGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, TypeToken<?> type) {
        this.gson = gson;
        this.adapter = adapter;
        this.type = type;
    }

    @SuppressLint("LongLogTag")
    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String string = value.string();
            string = EncryptManager.newInstance().decrypt(string);//解密

            final T read;
            read = new Gson().fromJson(string, type.getType());
//            final Reader reader = value.charStream();
//            JsonReader jsonReader = gson.newJsonReader(reader);
//            final T read = adapter.read(jsonReader);
            return read;

        } finally {
            value.close();
        }
    }
}
