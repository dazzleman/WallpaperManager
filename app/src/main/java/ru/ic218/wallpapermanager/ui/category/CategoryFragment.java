package ru.ic218.wallpapermanager.ui.category;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.ui.category.adapter.CategoryRecycleViewAdapter;

public class CategoryFragment extends MvpLceFragment<SwipeRefreshLayout, CategoryRecycleViewAdapter, CategoryView, CategoryPresenter> implements CategoryView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.rvCategory)
    RecyclerView rvCategory;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment getInstance(){
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        contentView.setOnRefreshListener(this);
        if(getString(R.string.is_land).equals("false")){
            rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            rvCategory.setLayoutManager(new GridLayoutManager(getContext(),3));
        }
        rvCategory.setHasFixedSize(true);
        loadData(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @NonNull
    @Override
    public CategoryPresenter createPresenter() {
        return new CategoryPresenter();
    }

    @Override
    public void setData(CategoryRecycleViewAdapter adapter) {
        rvCategory.setAdapter(adapter);
    }

    @Override
    public void loadData(boolean pullToRefresh) {

        presenter.init(true);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(pullToRefresh);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        contentView.setRefreshing(pullToRefresh);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
}
