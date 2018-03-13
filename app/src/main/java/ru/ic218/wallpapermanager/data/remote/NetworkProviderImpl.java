package ru.ic218.wallpapermanager.data.remote;

import java.util.Map;

import retrofit2.Call;
import ru.ic218.wallpapermanager.model.Photo;

/**
 * @author Nikolay Vlaskin on 12.03.2018.
 */

public class NetworkProviderImpl implements NetworkProvider {

    private Api api;

    public NetworkProviderImpl(Api api) {
        this.api = api;
    }

    @Override
    public Call<Photo> getPhotoByOptions(Map<String, String> options) {
        return api.getPhotoByOptions(Api.API_KEY, options);
    }

    @Override
    public Call<Photo> getPhotoByCategory(String category) {
        return api.getPhotoByCategory(Api.API_KEY, category);
    }
}
