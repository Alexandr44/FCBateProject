package com.alex44.fcbate.team.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.team.model.room.RoomPlayer;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomPlayerDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomPlayer roomPlayer);

    @Insert(onConflict = REPLACE)
    void insert(RoomPlayer... roomPlayers);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomPlayer> roomPlayerList);

    @Update
    void update(RoomPlayer roomPlayer);

    @Update
    void update(RoomPlayer... roomPlayers);

    @Update
    void update(List<RoomPlayer> roomPlayerList);

    @Delete
    void delete(RoomPlayer roomPlayer);

    @Delete
    void delete(RoomPlayer... roomPlayers);

    @Delete
    void delete(List<RoomPlayer> roomPlayerList);

    @Query("SELECT * FROM RoomPlayer rp where rp.id = :id")
    RoomPlayer findById(Long id);

    @Query("SELECT * FROM RoomPlayer")
    List<RoomPlayer> findAll();

    @Query("DELETE FROM RoomPlayer")
    void deleteAll();
}
