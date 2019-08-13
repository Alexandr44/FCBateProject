package com.alex44.fcbate.tournament.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.tournament.model.room.RoomTournamentInfo;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomTournamentInfoDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomTournamentInfo roomTournamentInfo);

    @Insert(onConflict = REPLACE)
    void insert(RoomTournamentInfo... roomTournamentInfos);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomTournamentInfo> roomTournamentInfoList);

    @Update
    void update(RoomTournamentInfo roomTournamentInfo);

    @Update
    void update(RoomTournamentInfo... roomTournamentInfos);

    @Update
    void update(List<RoomTournamentInfo> roomTournamentInfoList);

    @Delete
    void delete(RoomTournamentInfo roomTournamentInfo);

    @Delete
    void delete(RoomTournamentInfo... roomTournamentInfos);

    @Delete
    void delete(List<RoomTournamentInfo> roomTournamentInfoList);

    @Query("SELECT * FROM RoomTournamentInfo i ORDER BY i.points desc LIMIT 3")
    List<RoomTournamentInfo> findFirstThree();

    @Query("SELECT * FROM RoomTournamentInfo i where i.position = :position LIMIT 1")
    RoomTournamentInfo findByPosition(Long position);
}
