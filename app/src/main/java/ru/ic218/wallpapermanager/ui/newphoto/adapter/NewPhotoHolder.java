package ru.ic218.wallpapermanager.ui.newphoto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.model.Hit;

/**
 * @author Nikolay Vlaskin on 12.03.2018.
 */

class NewPhotoHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageNewPhoto)
    ImageView photo;

    NewPhotoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    void bind(Hit item){
        Glide.with(photo.getContext())
                .load(item.getWebformatURL())
                .into(photo);

        photo.setOnClickListener((view)-> {
            System.out.println("ID item: " + item.getId());
        });
    }
}
