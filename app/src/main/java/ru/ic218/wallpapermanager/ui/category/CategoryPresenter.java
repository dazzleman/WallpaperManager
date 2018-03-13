package ru.ic218.wallpapermanager.ui.category;

import android.util.SparseArray;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.security.*;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ic218.wallpapermanager.data.RepositoryProviderImpl;
import ru.ic218.wallpapermanager.model.CategoryPhoto;
import ru.ic218.wallpapermanager.model.Hit;
import ru.ic218.wallpapermanager.model.Photo;
import ru.ic218.wallpapermanager.ui.category.adapter.CategoryRecycleViewAdapter;
import ru.ic218.wallpapermanager.utils.Logger;

/**
 * Created by Home user on 10.03.2018.
 */

class CategoryPresenter extends MvpBasePresenter<CategoryView> {

    private CategoryRecycleViewAdapter adapter;
    private SparseArray<CategoryPhoto> categoryMap;

    void init(final boolean pullToRefresh, String[] categoryArray, String[] categoryArrayTranslate){
        if(adapter == null || adapter.getItemCount() == 0){
            categoryMap = new SparseArray<>();
            Random random = new SecureRandom();
            for (int i = 0; i < categoryArray.length; i++) {
                String item = categoryArray[i];
                categoryMap.put(i, new CategoryPhoto("", item, categoryArrayTranslate[i]));
                getPhotoUrlFromRemote(random, item);
            }
            adapter = new CategoryRecycleViewAdapter(categoryMap);
        }

        ifViewAttached((view) -> {
            view.setData(adapter);
            view.showContent();
        });
    }

    private void getPhotoUrlFromRemote(Random random, String item) {
        RepositoryProviderImpl.getInstance()
                .getPhotoByCategory(item)
                .enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call, Response<Photo> response) {
                        if (response.isSuccessful()) {
                            String query = response.raw().request().url().encodedQuery();
                            String category = parseResponse(query);
                            try {
                                Hit hit = response.body().getHits().get(random.nextInt(9));
                                adapter.updateMap(category, hit);
                                Logger.log("Category: " + category + " url: " + hit.getPreviewURL());
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                    }
                });
    }

    private String parseResponse(String response){
        final String[] pairs = response.split("&");
        String result = "";
        for (String pair : pairs){
            if(pair.contains("category")){
                int index = pair.indexOf("=");
                result = pair.substring(index + 1);
            }
        }
        return result;
    }
}
