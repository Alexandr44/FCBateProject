package com.alex44.fcbate.home.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.home.model.room.RoomNews;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomNewsDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomNews roomNews);

    @Insert(onConflict = REPLACE)
    void insert(RoomNews... roomNews);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomNews> roomNewsList);

    @Update
    void update(RoomNews roomNews);

    @Update
    void update(RoomNews... roomNews);

    @Update
    void update(List<RoomNews> roomNewsList);

    @Delete
    void delete(RoomNews roomNews);

    @Delete
    void delete(RoomNews... roomNews);

    @Delete
    void delete(List<RoomNews> roomNewsList);

    @Query("SELECT * FROM RoomNews m ORDER BY m.created desc LIMIT 5")
    List<RoomNews> findLastFive();
}
