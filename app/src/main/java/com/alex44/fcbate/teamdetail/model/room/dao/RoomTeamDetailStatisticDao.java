package com.alex44.fcbate.teamdetail.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.teamdetail.model.room.RoomTeamDetailStatistic;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomTeamDetailStatisticDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomTeamDetailStatistic roomTeamDetailStatistic);

    @Insert(onConflict = REPLACE)
    void insert(RoomTeamDetailStatistic... roomTeamDetailStatistics);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomTeamDetailStatistic> roomTeamDetailStatisticList);

    @Update
    void update(RoomTeamDetailStatistic roomTeamDetailStatistic);

    @Update
    void update(RoomTeamDetailStatistic... roomTeamDetailStatistics);

    @Update
    void update(List<RoomTeamDetailStatistic> roomTeamDetailStatisticList);

    @Delete
    void delete(RoomTeamDetailStatistic roomTeamDetailStatistic);

    @Delete
    void delete(RoomTeamDetailStatistic... roomTeamDetailStatistics);

    @Delete
    void delete(List<RoomTeamDetailStatistic> roomTeamDetailStatisticList);

    @Query("SELECT * FROM RoomTeamDetailStatistic rtds where rtds.playerId = :playerId")
    List<RoomTeamDetailStatistic> findByPlayerId(Long playerId);

}
