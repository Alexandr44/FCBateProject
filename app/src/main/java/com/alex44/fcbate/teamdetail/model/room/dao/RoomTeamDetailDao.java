package com.alex44.fcbate.teamdetail.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.teamdetail.model.room.RoomTeamDetail;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomTeamDetailDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomTeamDetail roomTeamDetail);

    @Insert(onConflict = REPLACE)
    void insert(RoomTeamDetail... roomTeamDetails);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomTeamDetail> roomTeamDetailList);

    @Update
    void update(RoomTeamDetail roomTeamDetail);

    @Update
    void update(RoomTeamDetail... roomTeamDetails);

    @Update
    void update(List<RoomTeamDetail> roomTeamDetailList);

    @Delete
    void delete(RoomTeamDetail roomTeamDetail);

    @Delete
    void delete(RoomTeamDetail... roomTeamDetails);

    @Delete
    void delete(List<RoomTeamDetail> roomTeamDetailList);

    @Query("SELECT * FROM RoomTeamDetail rtd where rtd.detailId = :detailId")
    RoomTeamDetail findByDetailId(String detailId);

}
