package com.alex44.fcbate.common.navigation;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.home.ui.HomeFragment;
import com.alex44.fcbate.news.ui.NewsFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class HomeScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return HomeFragment.newInstance();
        }
    }

    public static class NewsScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return NewsFragment.newInstance();
        }
    }

}
