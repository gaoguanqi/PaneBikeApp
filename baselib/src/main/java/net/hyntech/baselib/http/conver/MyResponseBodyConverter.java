package net.hyntech.baselib.http.conver;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import net.hyntech.baselib.error.ApiException;
import net.hyntech.baselib.http.BaseResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private final Charset UTF_8 = Charset.forName("UTF-8");;
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
        //核心代码:  判断 status 是否是后台定义的正常值
        if (TextUtils.isEmpty(baseResponse.getCode())) {
            value.close();
            throw new ApiException("数据异常！！！");
        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}

