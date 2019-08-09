package com.alex44.fcbate.common.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alex44.fcbate.home.model.room.RoomMatch;
import com.alex44.fcbate.home.model.room.RoomNews;
import com.alex44.fcbate.home.model.room.RoomTeam;
import com.alex44.fcbate.home.model.room.RoomTournament;
import com.alex44.fcbate.home.model.room.RoomTournamentInfo;
import com.alex44.fcbate.home.model.room.dao.RoomMatchDao;
import com.alex44.fcbate.home.model.room.dao.RoomNewsDao;
import com.alex44.fcbate.home.model.room.dao.RoomTeamDao;
import com.alex44.fcbate.home.model.room.dao.RoomTournamentDao;
import com.alex44.fcbate.home.model.room.dao.RoomTournamentInfoDao;
import com.alex44.fcbate.newsdetail.model.room.RoomNewsDetail;
import com.alex44.fcbate.newsdetail.model.room.dao.RoomNewsDetailDao;

@Database(entities = {RoomMatch.class, RoomNews.class, RoomTeam.class, RoomTournament.class, RoomTournamentInfo.class,
        RoomNewsDetail.class}, version = 1)
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
    public abstract RoomNewsDao getNewsDao();
    public abstract RoomTeamDao getTeamDao();
    public abstract RoomTournamentDao getTournamentDao();
    public abstract RoomTournamentInfoDao getTournamentInfoDao();

    public abstract RoomNewsDetailDao getNewsDetailDao();

}
