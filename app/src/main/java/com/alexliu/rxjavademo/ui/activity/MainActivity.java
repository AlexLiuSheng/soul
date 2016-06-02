package com.alexliu.rxjavademo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.alexliu.rxjavademo.R;
import com.alexliu.rxjavademo.ui.BaseActivity;
import com.alexliu.rxjavademo.ui.fragment.IPFragment;
import com.alexliu.rxjavademo.ui.fragment.MusicFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private SparseArray<Fragment> fragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        init();
        initData();
    }

    private void initData() {
        fragmentSparseArray=  new SparseArray<>();
        fragmentSparseArray.append(0, new IPFragment());
        fragmentSparseArray.append(1,new MusicFragment());
        for(int i=0;i<fragmentSparseArray.size();i++){
            getSupportFragmentManager().beginTransaction().add(R.id.container,fragmentSparseArray.get(i)).commit();
        }
        startFragment(IPFragment.class);
    }

    private void init() {
        setSupportActionBar(toolbar);
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                actionBarDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                actionBarDrawerToggle.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                actionBarDrawerToggle.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                actionBarDrawerToggle.onDrawerStateChanged(newState);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.item_ip:
                        startFragment(IPFragment.class);
                        break;
                    case R.id.item_music:
                      startFragment(MusicFragment.class);
                        break;
                }
                return true;
            }
        });


    }

    private void startFragment(Class<?> fragmentClass) {
        for (int i = 0; i < fragmentSparseArray.size(); i++) {
            Fragment fragment = fragmentSparseArray.get(i);
            if (fragment.getClass().equals(fragmentClass)) {
                getSupportFragmentManager().beginTransaction().show(fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();
            }
        }
    }
}

