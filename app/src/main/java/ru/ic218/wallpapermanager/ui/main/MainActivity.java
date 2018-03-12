package ru.ic218.wallpapermanager.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.ui.category.CategoryFragment;
import ru.ic218.wallpapermanager.ui.main.adapter.ViewPagerFragmentAdapter;
import ru.ic218.wallpapermanager.ui.newphoto.NewPhotoFragment;
import ru.ic218.wallpapermanager.ui.popular.PopularFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ViewPagerFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initTabLayout();
        initViewPager();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initTabLayout() {
        tabs.setupWithViewPager(viewpager);
    }

    private void initViewPager() {
        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(),
                new ArrayList<>(Arrays.asList(
                        getString(R.string.title_category_fragment),
                        getString(R.string.title_new_photo_fragment),
                        getString(R.string.title_popular_fragment)
                )),
                new ArrayList<>(Arrays.asList(
                        CategoryFragment.getInstance(),
                        NewPhotoFragment.getInstance(),
                        PopularFragment.getInstance()
                )));
        viewpager.setAdapter(adapter);
    }
}
