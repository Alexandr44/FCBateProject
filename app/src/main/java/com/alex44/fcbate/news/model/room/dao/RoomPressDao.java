package com.alex44.fcbate.news.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.news.model.room.RoomPress;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomPressDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomPress roomPress);

    @Insert(onConflict = REPLACE)
    void insert(RoomPress... roomPress);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomPress> roomPressList);

    @Update
    void update(RoomPress roomPress);

    @Update
    void update(RoomPress... roomPress);

    @Update
    void update(List<RoomPress> roomPressList);

    @Delete
    void delete(RoomPress roomPress);

    @Delete
    void delete(RoomPress... roomPress);

    @Delete
    void delete(List<RoomPress> roomPressList);

    @Query("SELECT * FROM RoomPress p ORDER BY p.created desc LIMIT :count")
    List<RoomPress> findLast(int count);

    @Query("SELECT * FROM RoomPress p where p.id = :id LIMIT 1")
    RoomPress findById(Long id);
}
