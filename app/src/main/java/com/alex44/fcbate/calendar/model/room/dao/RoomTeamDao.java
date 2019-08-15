package com.alex44.fcbate.calendar.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.calendar.model.room.RoomTeam;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomTeamDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomTeam roomTeam);

    @Insert(onConflict = REPLACE)
    void insert(RoomTeam... roomTeams);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomTeam> roomTeamList);

    @Update
    void update(RoomTeam roomTeam);

    @Update
    void update(RoomTeam... roomTeams);

    @Update
    void update(List<RoomTeam> roomTeamList);

    @Delete
    void delete(RoomTeam roomTeam);

    @Delete
    void delete(RoomTeam... roomTeams);

    @Delete
    void delete(List<RoomTeam> roomTeamList);

    @Query("SELECT * FROM RoomTeam WHERE id = :id LIMIT 1")
    RoomTeam findById(long id);

}
