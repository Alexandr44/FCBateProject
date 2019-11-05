package com.alex44.fcbate.main.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.about.ui.AboutFragment;
import com.alex44.fcbate.calendar.ui.CalendarFragment;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.home.ui.HomeFragment;
import com.alex44.fcbate.main.presenter.MainPresenter;
import com.alex44.fcbate.main.view.MainView;
import com.alex44.fcbate.news.ui.NewsFragment;
import com.alex44.fcbate.team.ui.TeamFragment;
import com.alex44.fcbate.tournament.ui.TournamentFragment;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

public class MainActivity  extends MvpAppCompatActivity implements MainView,
         NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    MainPresenter presenter;

    @Inject
    protected NavigatorHolder navigatorHolder;

    @Inject
    protected Router router;

    private Navigator navigator = new SupportAppNavigator(this, R.id.menu_fragment_layout) {
        @Override
        protected void setupFragmentTransaction(Command command, Fragment currentFragment, Fragment nextFragment, FragmentTransaction fragmentTransaction) {
            super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction);
            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
        }
    };

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
        App.getInstance().getAppComponent().inject(this);
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

    @ProvidePresenter
    protected MainPresenter createPresenter() {
        final MainPresenter mainPresenter = new MainPresenter();
        App.getInstance().getAppComponent().inject(mainPresenter);
        return mainPresenter;
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu_fragment_layout);
            if (fragment instanceof BackButtonListener && ((BackButtonListener) fragment).backClick()) {
                return;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
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
            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu_fragment_layout);
            if (!(fragment instanceof HomeFragment)) {
                presenter.goToHomeScreen();
            }
        } else if (id == R.id.nav_news) {
            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu_fragment_layout);
            if (!(fragment instanceof NewsFragment)) {
                presenter.goToNewsScreen();
            }
        } else if (id == R.id.nav_calendar) {
            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu_fragment_layout);
            if (!(fragment instanceof CalendarFragment)) {
                presenter.goToCalendarScreen();
            }
        } else if (id == R.id.nav_table) {
            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu_fragment_layout);
            if (!(fragment instanceof TournamentFragment)) {
                presenter.goToTableScreen();
            }
        } else if (id == R.id.nav_team) {
            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu_fragment_layout);
            if (!(fragment instanceof TeamFragment)) {
                presenter.goToTeamScreen();
            }
        } else if (id == R.id.nav_club) {
            final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.menu_fragment_layout);
            if (!(fragment instanceof AboutFragment)) {
                presenter.goToClubScreen();
            }
        } else if (id == R.id.nav_app) {
            presenter.goToAppScreen();
        }

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
