package com.alex44.fcbate.main.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alex44.fcbate.R;
import com.alex44.fcbate.home.ui.HomeFragment;
import com.alex44.fcbate.main.presenter.MainPresenter;
import com.alex44.fcbate.main.view.MainView;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity  extends MvpAppCompatActivity implements MainView,
         NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    MainPresenter presenter;

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public void init() {
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }


    @Override
    public void goToHomeScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_fragment_layout, new HomeFragment())
                .commit();
    }

    @Override
    public void goToNewsScreen() {

    }

    @Override
    public void goToCalendarScreen() {

    }

    @Override
    public void goToTableScreen() {

    }

    @Override
    public void goToTeamScreen() {

    }

    @Override
    public void goToClubScreen() {

    }

    @Override
    public void goToAppScreen() {

    }

    @ProvidePresenter
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.nav_home) {
            presenter.selectedHome();
        } else if (id == R.id.nav_news) {
            presenter.selectedNews();
        } else if (id == R.id.nav_calendar) {
            presenter.selectedCalendar();
        } else if (id == R.id.nav_table) {
            presenter.selectedTable();
        } else if (id == R.id.nav_team) {
            presenter.selectedTeam();
        } else if (id == R.id.nav_club) {
            presenter.selectedClub();
        } else if (id == R.id.nav_app) {
            presenter.selectedApp();
        }

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
