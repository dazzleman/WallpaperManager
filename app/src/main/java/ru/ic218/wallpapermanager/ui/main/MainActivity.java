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
import ru.ic218.wallpapermanager.data.RepositoryProviderImpl;
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

        RepositoryProviderImpl.getInstance();

        initToolbar();
        initTabLayout();
        initViewPager();


/*        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("pixabay.com", 443);
                    socket.startHandshake();
                    SSLSession session = socket.getSession();
                    String s = session.getProtocol();
                    String s1 = session.getCipherSuite();
                    Log.d("myLogs", session.getProtocol());
                    Log.d("myLogs", session.getCipherSuite());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Request request2 = new Request.Builder()
                        .url("https://pixabay.com/api/?per_page=3&key=8333120-9bedd8456e4d45460ee77b828&pretty=true&category=fashion&min_width=340")
                        .build();

                CustomTrust2 okHttp = new CustomTrust2();
                try {
                    try (Response response = okHttp.client().newCall(request2).execute()) {
                        if (!response.isSuccessful())
                            throw new IOException("Unexpected code " + response);

                        ResponseBody body = response.body();
                        String b = body.string();
                        System.out.println(b);
                        Headers responseHeaders = response.headers();
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
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
