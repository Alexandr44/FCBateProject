package com.alex44.fcbate.teamdetail.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.teamdetail.model.room.RoomTeamDetailPhoto;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomTeamDetailPhotoDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomTeamDetailPhoto roomTeamDetailPhoto);

    @Insert(onConflict = REPLACE)
    void insert(RoomTeamDetailPhoto... roomTeamDetailPhotos);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomTeamDetailPhoto> roomTeamDetailPhotoList);

    @Update
    void update(RoomTeamDetailPhoto roomTeamDetailPhoto);

    @Update
    void update(RoomTeamDetailPhoto... roomTeamDetailPhotos);

    @Update
    void update(List<RoomTeamDetailPhoto> roomTeamDetailPhotoList);

    @Delete
    void delete(RoomTeamDetailPhoto roomTeamDetailPhoto);

    @Delete
    void delete(RoomTeamDetailPhoto... roomTeamDetailPhotos);

    @Delete
    void delete(List<RoomTeamDetailPhoto> roomTeamDetailPhotoList);

    @Query("SELECT * FROM RoomTeamDetailPhoto rtdp where rtdp.memberId = :memberId")
    List<RoomTeamDetailPhoto> findByMemberId(String memberId);

}
