package ru.ic218.wallpapermanager.ui.newphoto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.ic218.wallpapermanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPhotoFragment extends Fragment {


    public NewPhotoFragment() {
        // Required empty public constructor
    }

    public static NewPhotoFragment getInstance(){
        return new NewPhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_photo, container, false);
    }

}
