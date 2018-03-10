package ru.ic218.wallpapermanager.ui.category;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import ru.ic218.wallpapermanager.model.CategoryPhoto;
import ru.ic218.wallpapermanager.ui.category.adapter.CategoryRecycleViewAdapter;

/**
 * Created by Home user on 10.03.2018.
 */

public class CategoryPresenter extends MvpBasePresenter<CategoryView> {

    private CategoryRecycleViewAdapter adapter;

    void init(final boolean pullToRefresh){

        adapter = new CategoryRecycleViewAdapter(CategoryPhoto.getCategory());

        ifViewAttached((view) -> {
            view.setData(adapter);
            view.showContent();
        });
    }
}
