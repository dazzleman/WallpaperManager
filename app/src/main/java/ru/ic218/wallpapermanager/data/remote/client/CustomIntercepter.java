package ru.ic218.wallpapermanager.data.remote.client;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Nikolay Vlaskin on 15.03.2018.
 */

public class CustomIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request().newBuilder().build();
        Request request2 = new Request.Builder()
                .url("https://pixabay.com/api/?per_page=3&key=8333120-9bedd8456e4d45460ee77b828&pretty=true&category=fashion&min_width=340")
                .build();

        CustomTrust2 okHttp = new CustomTrust2();
        try (Response response = okHttp.client().newCall(request2).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody body = response.body();
            String b = body.string();
            System.out.println(b);
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            System.out.println(response.body().string());
        }
        return null;
    }
}
