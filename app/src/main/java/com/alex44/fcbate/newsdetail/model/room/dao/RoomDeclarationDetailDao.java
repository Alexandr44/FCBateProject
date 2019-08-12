package com.alex44.fcbate.newsdetail.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.newsdetail.model.room.RoomDeclarationDetail;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomDeclarationDetailDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomDeclarationDetail roomDeclarationDetail);

    @Insert(onConflict = REPLACE)
    void insert(RoomDeclarationDetail... roomDeclarationDetails);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomDeclarationDetail> roomDeclarationDetails);

    @Update
    void update(RoomDeclarationDetail roomDeclarationDetail);

    @Update
    void update(RoomDeclarationDetail... roomDeclarationDetails);

    @Update
    void update(List<RoomDeclarationDetail> roomDeclarationDetails);

    @Delete
    void delete(RoomDeclarationDetail roomDeclarationDetail);

    @Delete
    void delete(RoomDeclarationDetail... roomDeclarationDetails);

    @Delete
    void delete(List<RoomDeclarationDetail> roomDeclarationDetails);

    @Query("SELECT * FROM RoomDeclarationDetail d where d.id = :id LIMIT 1")
    RoomDeclarationDetail findById(Long id);

}
