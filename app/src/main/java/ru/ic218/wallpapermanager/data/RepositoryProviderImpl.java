package ru.ic218.wallpapermanager.data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.ic218.wallpapermanager.data.remote.Api;
import ru.ic218.wallpapermanager.data.remote.NetworkProvider;
import ru.ic218.wallpapermanager.data.remote.NetworkProviderImpl;
import ru.ic218.wallpapermanager.model.Photo;

/**
 * @author Nikolay Vlaskin on 12.03.2018.
 */

public final class RepositoryProviderImpl implements RepositoryProvider {

    private static RepositoryProviderImpl INSTANCE = null;
    private NetworkProvider networkProvider;

    public static RepositoryProviderImpl getInstance() {
        if (INSTANCE == null) {
            return new RepositoryProviderImpl();
        } else return INSTANCE;
    }

    private RepositoryProviderImpl() {
        Api api = new Retrofit.Builder()
                .baseUrl("https://pixabay.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api.class);

        networkProvider = new NetworkProviderImpl(api);
    }


    @Override
    public Call<Photo> getPhotoByOptions(Map<String, String> options) {
        return networkProvider.getPhotoByOptions(options);
    }

    @Override
    public Call<Photo> getPhotoByCategory(String category) {
        return networkProvider.getPhotoByCategory(category);
    }
}
