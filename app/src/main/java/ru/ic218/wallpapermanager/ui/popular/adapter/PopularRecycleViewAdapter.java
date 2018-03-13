package ru.ic218.wallpapermanager.ui.popular.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.model.Hit;

/**
 * Created by Home user on 10.03.2018.
 */

public class PopularRecycleViewAdapter extends RecyclerView.Adapter<PopularHolder> {

    private List<Hit> listPhoto;

    public PopularRecycleViewAdapter(List<Hit> listPhoto) {
        this.listPhoto = listPhoto;
    }

    @Override
    public PopularHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_popular_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(PopularHolder holder, int position) {
        holder.bind(listPhoto.get(position));
    }

    @Override
    public int getItemCount() {
        return (listPhoto == null) ? 0 : listPhoto.size();
    }

    public void setData(List<Hit> photo) {
        int insertPosition = listPhoto.size();
        listPhoto.addAll(photo);
        if (insertPosition == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(insertPosition + 1, photo.size());
        }
    }
}
