package com.alex44.fcbate.common.navigation;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.about.ui.AboutFragment;
import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.calendar.ui.CalendarFragment;
import com.alex44.fcbate.home.ui.HomeFragment;
import com.alex44.fcbate.info.ui.InfoFragment;
import com.alex44.fcbate.match.ui.MatchFragment;
import com.alex44.fcbate.news.model.enums.NewsItemType;
import com.alex44.fcbate.news.ui.NewsFragment;
import com.alex44.fcbate.newsdetail.ui.NewsDetailFragment;
import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.team.ui.TeamFragment;
import com.alex44.fcbate.teamdetail.ui.TeamDetailFragment;
import com.alex44.fcbate.tournament.ui.TournamentFragment;
import com.alex44.fcbate.video.ui.VideoFragment;

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

    public static class NewsDetailScreen extends SupportAppScreen {
        private NewsItemType type;
        private Long id;

        public NewsDetailScreen(NewsItemType type, Long id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public Fragment getFragment() {
            return NewsDetailFragment.newInstance(type, id);
        }
    }

    public static class TournamentScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return TournamentFragment.newInstance();
        }
    }

    public static class CalendarScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return CalendarFragment.newInstance();
        }
    }

    public static class TeamScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return TeamFragment.newInstance();
        }
    }

    public static class TeamDetailScreen extends SupportAppScreen {
        private TeamItemType type;
        private Long id;

        public TeamDetailScreen(TeamItemType type, Long id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public Fragment getFragment() {
            return TeamDetailFragment.newInstance(type, id);
        }
    }

    public static class AboutScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return AboutFragment.newInstance();
        }
    }

    public static class InfoScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return InfoFragment.newInstance();
        }
    }

    public static class MatchScreen extends SupportAppScreen {
        private MatchDTO matchDTO;

        public MatchScreen(MatchDTO matchDTO) {
            this.matchDTO = matchDTO;
        }

        @Override
        public Fragment getFragment() {
            return MatchFragment.newInstance(matchDTO);
        }
    }

    public static class VideoScreen extends SupportAppScreen {
        private String videoId;

        public VideoScreen(String videoId) {
            this.videoId = videoId;
        }

        @Override
        public Fragment getFragment() {
            return VideoFragment.newInstance(videoId);
        }
    }

}
