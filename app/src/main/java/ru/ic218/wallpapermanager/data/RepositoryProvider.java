package ru.ic218.wallpapermanager.data;

import java.util.Map;

import retrofit2.Call;
import ru.ic218.wallpapermanager.model.Photo;

/**
 * @author Nikolay Vlaskin on 12.03.2018.
 */

public interface RepositoryProvider {

    Call<Photo> getPhotoByOptions(Map<String, String> options);
    Call<Photo> getPhotoByCategory(String category);
}
