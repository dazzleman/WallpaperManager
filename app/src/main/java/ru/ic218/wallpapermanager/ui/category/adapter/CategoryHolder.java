package ru.ic218.wallpapermanager.ui.category.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.model.CategoryPhoto;

/**
 * Created by Home user on 10.03.2018.
 */

public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.imageCategory) ImageView photo;
    @BindView(R.id.titleCategory) TextView title;

    CategoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(CategoryPhoto item){
        title.setText(item.getTitleTranslate());
        if(!item.getPhotoUrl().equals("")){
            Glide.with(photo.getContext())
                    .load(item.getPhotoUrl())
                    .into(photo);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
