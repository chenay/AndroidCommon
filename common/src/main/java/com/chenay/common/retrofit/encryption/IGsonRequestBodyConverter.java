package com.chenay.common.retrofit.encryption;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.ByteString;
import retrofit2.Converter;

/**
 * Created by Y.Chen5 on 5/2/2018.
 */
public class IGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final String TAG = "IGsonRequestBodyConverter";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    IGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @SuppressLint("LongLogTag")
    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        final ByteString content = buffer.readByteString();
        String str = content.utf8();
        str=  EncryptManager.newInstance().encrypt(str);
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE, str);
        return requestBody;
    }
}

