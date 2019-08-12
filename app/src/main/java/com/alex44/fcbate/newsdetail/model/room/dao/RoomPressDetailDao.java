package com.alex44.fcbate.newsdetail.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.newsdetail.model.room.RoomPressDetail;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomPressDetailDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomPressDetail roomPressDetail);

    @Insert(onConflict = REPLACE)
    void insert(RoomPressDetail... roomPressDetails);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomPressDetail> roomPressDetails);

    @Update
    void update(RoomPressDetail roomPressDetail);

    @Update
    void update(RoomPressDetail... roomPressDetails);

    @Update
    void update(List<RoomPressDetail> roomPressDetails);

    @Delete
    void delete(RoomPressDetail roomPressDetail);

    @Delete
    void delete(RoomPressDetail... roomPressDetails);

    @Delete
    void delete(List<RoomPressDetail> roomPressDetails);

    @Query("SELECT * FROM RoomPressDetail d where d.id = :id LIMIT 1")
    RoomPressDetail findById(Long id);

}
