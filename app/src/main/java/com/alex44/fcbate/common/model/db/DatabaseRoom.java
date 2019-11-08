package com.alex44.fcbate.common.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alex44.fcbate.about.model.room.RoomAbout;
import com.alex44.fcbate.about.model.room.dao.RoomAboutDao;
import com.alex44.fcbate.calendar.model.room.RoomMatch;
import com.alex44.fcbate.calendar.model.room.RoomTeam;
import com.alex44.fcbate.calendar.model.room.RoomTournament;
import com.alex44.fcbate.calendar.model.room.dao.RoomMatchDao;
import com.alex44.fcbate.calendar.model.room.dao.RoomTeamDao;
import com.alex44.fcbate.calendar.model.room.dao.RoomTournamentDao;
import com.alex44.fcbate.news.model.room.RoomDeclaration;
import com.alex44.fcbate.news.model.room.RoomNews;
import com.alex44.fcbate.news.model.room.RoomPress;
import com.alex44.fcbate.news.model.room.dao.RoomDeclarationDao;
import com.alex44.fcbate.news.model.room.dao.RoomNewsDao;
import com.alex44.fcbate.news.model.room.dao.RoomPressDao;
import com.alex44.fcbate.newsdetail.model.room.RoomDeclarationDetail;
import com.alex44.fcbate.newsdetail.model.room.RoomNewsDetail;
import com.alex44.fcbate.newsdetail.model.room.RoomPressDetail;
import com.alex44.fcbate.newsdetail.model.room.dao.RoomDeclarationDetailDao;
import com.alex44.fcbate.newsdetail.model.room.dao.RoomNewsDetailDao;
import com.alex44.fcbate.newsdetail.model.room.dao.RoomPressDetailDao;
import com.alex44.fcbate.tournament.model.room.RoomTournamentInfo;
import com.alex44.fcbate.tournament.model.room.dao.RoomTournamentInfoDao;

@Database(entities = {RoomMatch.class, RoomTeam.class, RoomTournament.class, RoomTournamentInfo.class,
        RoomNewsDetail.class, RoomPressDetail.class, RoomDeclarationDetail.class,
        RoomNews.class, RoomPress.class, RoomDeclaration.class,
        RoomAbout.class}, version = 1)
public abstract class DatabaseRoom extends RoomDatabase {

    private static final String DATABASE_NAME = "RoomDatabase.db";

    private static volatile DatabaseRoom instance;

    public static synchronized DatabaseRoom getInstance() {
        if (instance == null) {
            throw new RuntimeException("Database have not been created");
        }
        return instance;
    }

    public static void create(Context context) {

//        final Migration migration1_2 = new Migration(1, 2) {
//            @Override
//            public void migrate(@NonNull SupportSQLiteDatabase database) {
//                Timber.d("Migration from 1 to 2");
//                database.execSQL("");
//            }
//        };

        if (instance == null) {
            instance = Room.databaseBuilder(
        context, DatabaseRoom.class, DATABASE_NAME)
//                    .addMigrations(migration1_2)
                    .build();
        }
    }

    public abstract RoomMatchDao getMatchDao();
    public abstract RoomTeamDao getTeamDao();
    public abstract RoomTournamentDao getTournamentDao();
    public abstract RoomTournamentInfoDao getTournamentInfoDao();

    public abstract RoomNewsDetailDao getNewsDetailDao();
    public abstract RoomPressDetailDao getPressDetailDao();
    public abstract RoomDeclarationDetailDao getDeclarationDetailDao();

    public abstract RoomNewsDao getNewsDao();
    public abstract RoomPressDao getPressDao();
    public abstract RoomDeclarationDao getDeclarationDao();

    public abstract RoomAboutDao getAboutDao();
}
