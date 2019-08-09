package com.alex44.fcbate.newsdetail.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.newsdetail.model.room.RoomNewsDetail;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomNewsDetailDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomNewsDetail roomNewsDetail);

    @Insert(onConflict = REPLACE)
    void insert(RoomNewsDetail... roomNewsDetails);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomNewsDetail> roomNewsDetails);

    @Update
    void update(RoomNewsDetail roomNewsDetail);

    @Update
    void update(RoomNewsDetail... roomNewsDetails);

    @Update
    void update(List<RoomNewsDetail> roomNewsDetails);

    @Delete
    void delete(RoomNewsDetail roomNewsDetail);

    @Delete
    void delete(RoomNewsDetail... roomNewsDetails);

    @Delete
    void delete(List<RoomNewsDetail> roomNewsDetails);

    @Query("SELECT * FROM RoomNewsDetail d where d.id = :id LIMIT 1")
    RoomNewsDetail findById(Long id);

}
