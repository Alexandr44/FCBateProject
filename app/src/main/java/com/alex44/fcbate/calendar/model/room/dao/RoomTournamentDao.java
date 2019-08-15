package com.alex44.fcbate.calendar.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.calendar.model.room.RoomTournament;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomTournamentDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomTournament roomTournament);

    @Insert(onConflict = REPLACE)
    void insert(RoomTournament... roomTournaments);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomTournament> roomTournamentList);

    @Update
    void update(RoomTournament roomTournament);

    @Update
    void update(RoomTournament... roomTournaments);

    @Update
    void update(List<RoomTournament> roomTournamentList);

    @Delete
    void delete(RoomTournament roomTournament);

    @Delete
    void delete(RoomTournament... roomTournaments);

    @Delete
    void delete(List<RoomTournament> roomTournamentList);

    @Query("SELECT * FROM RoomTournament WHERE id = :id LIMIT 1")
    RoomTournament findById(long id);

}
