package ru.ic218.wallpapermanager.ui.newphoto;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ic218.wallpapermanager.R;
import ru.ic218.wallpapermanager.common.EndlessRecyclerViewScrollListener;
import ru.ic218.wallpapermanager.common.SpacesItemDecoration;
import ru.ic218.wallpapermanager.ui.newphoto.adapter.NewPhotoRecycleViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPhotoFragment extends MvpLceFragment<SwipeRefreshLayout,
        NewPhotoRecycleViewAdapter, NewPhotoView, NewPhotoPresenter> implements NewPhotoView, SwipeRefreshLayout.OnRefreshListener {

    private static String SCROLL_POSITION = "scroll_position";

    @BindView(R.id.rvNewPhoto)
    RecyclerView rvNewPhoto;

    private EndlessRecyclerViewScrollListener scrollListener;

    public NewPhotoFragment() {
    }

    public static NewPhotoFragment getInstance() {
        return new NewPhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        contentView.setOnRefreshListener(this);
        GridLayoutManager layoutManager;
        if (getString(R.string.is_land).equals("false")) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 3);

        }
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.getLatestPhoto(page);
                System.out.println("totalItemsCount:" + totalItemsCount);
                System.out.println("Page:" + page);
            }
        };
        rvNewPhoto.setLayoutManager(layoutManager);
        rvNewPhoto.addOnScrollListener(scrollListener);
        rvNewPhoto.addItemDecoration(new SpacesItemDecoration(4));
        rvNewPhoto.setHasFixedSize(true);

        if (savedInstanceState != null && savedInstanceState.getInt(SCROLL_POSITION) != 0) {
            presenter.restoreAdapter();
        } else {
            loadData(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (rvNewPhoto != null && rvNewPhoto.getLayoutManager() != null) {
            outState.putInt(SCROLL_POSITION, ((GridLayoutManager) rvNewPhoto.getLayoutManager()).findFirstVisibleItemPosition());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getInt(SCROLL_POSITION) != 0) {
            rvNewPhoto.scrollToPosition(savedInstanceState.getInt(SCROLL_POSITION));
        }
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public NewPhotoPresenter createPresenter() {
        return new NewPhotoPresenter();
    }

    @Override
    public void setData(NewPhotoRecycleViewAdapter adapter) {
        rvNewPhoto.setAdapter(adapter);
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
        scrollListener.resetState();
        loadData(true);
    }
}
