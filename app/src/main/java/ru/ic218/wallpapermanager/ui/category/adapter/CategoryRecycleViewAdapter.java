package ru.ic218.wallpapermanager.ui.category.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.model.CategoryPhoto;

/**
 * Created by Home user on 10.03.2018.
 */

public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryHolder> {

    private List<CategoryPhoto> listCategory;

    public CategoryRecycleViewAdapter(List<CategoryPhoto> listCategory) {
        this.listCategory = listCategory;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_category_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.bind(listCategory.get(position));
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
}
