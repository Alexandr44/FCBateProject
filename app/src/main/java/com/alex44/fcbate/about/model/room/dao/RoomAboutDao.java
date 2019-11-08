package com.alex44.fcbate.about.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.about.model.room.RoomAbout;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomAboutDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomAbout roomTournamentInfo);

    @Update
    void update(RoomAbout roomTournamentInfo);

    @Delete
    void delete(RoomAbout roomTournamentInfo);

    @Query("SELECT * FROM RoomAbout ra WHERE ra.id = :id")
    RoomAbout findById(int id);

}
