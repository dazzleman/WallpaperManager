package ru.ic218.wallpapermanager.ui.category.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.model.CategoryPhoto;
import ru.ic218.wallpapermanager.model.Hit;

/**
 * Created by Home user on 10.03.2018.
 */

public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryHolder> {

    private List<CategoryPhoto> listCategory;
    private SparseArray<CategoryPhoto> mapCategory;

    public CategoryRecycleViewAdapter(List<CategoryPhoto> listCategory) {
        this.listCategory = listCategory;
    }

    public CategoryRecycleViewAdapter(SparseArray<CategoryPhoto> mapCategory) {
        this.mapCategory = mapCategory;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_category_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        //holder.bind(listCategory.get(position));
        holder.bind(mapCategory.get(position));
    }

    @Override
    public int getItemCount() {
        return mapCategory.size();
    }

    public void updateMap(String category, Hit photo) {
        for (int i = 0; i < mapCategory.size(); i++) {
            CategoryPhoto item = mapCategory.valueAt(i);
            if (item.getTitle().equals(category)){
                String url = photo.getWebformatURL().replace("_640", "_340");
                item.setPhotoUrl(url);
                System.out.println(url);
                notifyItemChanged(i);
            }
        }
    }
}
