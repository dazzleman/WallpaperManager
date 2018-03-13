package ru.ic218.wallpapermanager.data.remote;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ru.ic218.wallpapermanager.model.Photo;

/**
 * @author Nikolay Vlaskin on 12.03.2018.
 */

public interface Api {

    String API_KEY = "8333120-9bedd8456e4d45460ee77b828";

    @GET("api/?per_page=50")
    Call<Photo> getPhotoByOptions(@Query("key") String API_KEY, @QueryMap Map<String, String> options);

    @GET("api/?per_page=10&min_width=340")
    Call<Photo> getPhotoByCategory(@Query("key") String API_KEY, @Query("category") String category);
}
