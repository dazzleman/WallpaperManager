package ru.ic218.wallpapermanager.ui.category;

import android.util.SparseArray;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ic218.wallpapermanager.data.RepositoryProviderImpl;
import ru.ic218.wallpapermanager.model.CategoryPhoto;
import ru.ic218.wallpapermanager.model.Hit;
import ru.ic218.wallpapermanager.model.Photo;
import ru.ic218.wallpapermanager.ui.category.adapter.CategoryRecycleViewAdapter;

/**
 * Created by Home user on 10.03.2018.
 */

class CategoryPresenter extends MvpBasePresenter<CategoryView> {

    private CategoryRecycleViewAdapter adapter;
    private String[] categoryArray;
    private String[] categoryArrayTranslate;
    private SparseArray<CategoryPhoto> categoryMap;

    void init(final boolean pullToRefresh, String[] categoryArray, String[] categoryArrayTranslate){
        if(this.categoryArray == null){
            this.categoryArray = categoryArray;
            this.categoryArrayTranslate = categoryArrayTranslate;
            categoryMap = new SparseArray<>();
            for (int i = 0; i < categoryArray.length; i++) {
                String item = categoryArray[i];
                categoryMap.put(i, new CategoryPhoto("", item, categoryArrayTranslate[i]));
                Random random = new Random();
                RepositoryProviderImpl.getInstance()
                        .getPhotoByCategory(item)
                        .enqueue(new Callback<Photo>() {
                            @Override
                            public void onResponse(Call<Photo> call, Response<Photo> response) {
                                if (response.isSuccessful()) {
                                    String query = response.raw().request().url().encodedQuery();
                                    String category = parseResponse(query);
                                    Hit hit = response.body().getHits().get(random.nextInt(9));
                                    adapter.updateMap(category, hit);
                                    //System.out.println("Category: " + category + " url: " + hit.getPreviewURL());
                                }
                            }

                            @Override
                            public void onFailure(Call<Photo> call, Throwable t) {

                            }
                        });
            }
        }
        //adapter = new CategoryRecycleViewAdapter(CategoryPhoto.getCategory());
        adapter = new CategoryRecycleViewAdapter(categoryMap);

        ifViewAttached((view) -> {
            view.setData(adapter);
            view.showContent();
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
