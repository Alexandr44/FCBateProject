package com.alex44.fcbate.news.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.news.model.room.RoomNews;

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

    @Query("SELECT * FROM RoomNews n ORDER BY n.created desc LIMIT :count")
    List<RoomNews> findLast(int count);

    @Query("SELECT * FROM RoomNews n where n.id = :id LIMIT 1")
    RoomNews findById(Long id);
}
