package ru.ic218.wallpapermanager.ui.popular.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.model.Hit;

/**
 * @author Nikolay Vlaskin on 13.03.2018.
 */

public class PopularHolder extends RecyclerView.ViewHolder  {

    @BindView(R.id.imagePopular)
    ImageView photo;

    PopularHolder(View itemView) {
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
