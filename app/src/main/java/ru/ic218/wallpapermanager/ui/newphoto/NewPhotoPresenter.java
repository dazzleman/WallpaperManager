package ru.ic218.wallpapermanager.ui.newphoto;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ic218.wallpapermanager.data.RepositoryProviderImpl;
import ru.ic218.wallpapermanager.model.Photo;
import ru.ic218.wallpapermanager.ui.newphoto.adapter.NewPhotoRecycleViewAdapter;
import ru.ic218.wallpapermanager.utils.Logger;

/**
 * @author Nikolay Vlaskin on 12.03.2018.
 */

class NewPhotoPresenter extends MvpBasePresenter<NewPhotoView> {

    private NewPhotoRecycleViewAdapter adapter;

    void init(final boolean pullToRefresh) {
        adapter = new NewPhotoRecycleViewAdapter(new ArrayList<>());
        ifViewAttached((view) -> {
            view.setData(adapter);
            view.showContent();
        });

        getLatestPhoto(1);
    }

    void getLatestPhoto(int page) {
        Logger.log("Send Request");
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("order", "latest");
        map.put("orientation", "vertical");
        map.put("image_type", "photo");

        RepositoryProviderImpl.getInstance()
                .getPhotoByOptions(map)
                .enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call, Response<Photo> response) {
                        if (response.isSuccessful()) {
                            Photo photo = response.body();
                            adapter.setData(photo.getHits());
                        }
                        Logger.log("Get response");
                    }

                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        Logger.log(t.getMessage());
                    }
                });
    }

    void restoreAdapter() {
        if (adapter != null) {
            ifViewAttached((view) -> {
                view.setData(adapter);
                view.showContent();
            });
        }
    }
}
