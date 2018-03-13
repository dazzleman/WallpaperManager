package ru.ic218.wallpapermanager.ui.newphoto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.model.Hit;

/**
 * Created by Home user on 10.03.2018.
 */

public class NewPhotoRecycleViewAdapter extends RecyclerView.Adapter<NewPhotoHolder> {

    private List<Hit> listPhoto;

    public NewPhotoRecycleViewAdapter(List<Hit> listPhoto) {
        this.listPhoto = listPhoto;
    }

    @Override
    public NewPhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewPhotoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_new_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(NewPhotoHolder holder, int position) {
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
