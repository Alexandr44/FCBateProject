package com.alex44.fcbate.home.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.home.model.room.RoomMatch;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomMatchDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomMatch roomMatch);

    @Insert(onConflict = REPLACE)
    void insert(RoomMatch... roomMatchs);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomMatch> roomMatchList);

    @Update
    void update(RoomMatch roomMatch);

    @Update
    void update(RoomMatch... roomMatchs);

    @Update
    void update(List<RoomMatch> roomMatchList);

    @Delete
    void delete(RoomMatch roomMatch);

    @Delete
    void delete(RoomMatch... roomMatchs);

    @Delete
    void delete(List<RoomMatch> roomMatchList);

    @Query("SELECT * FROM (SELECT * FROM RoomMatch m ORDER BY m.date desc LIMIT 5) ORDER BY date asc")
    List<RoomMatch> findLastFive();

}
