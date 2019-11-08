package com.alex44.fcbate.team.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.team.model.room.RoomTrainer;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomTrainerDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomTrainer roomTrainer);

    @Insert(onConflict = REPLACE)
    void insert(RoomTrainer... roomTrainers);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomTrainer> roomTrainerList);

    @Update
    void update(RoomTrainer roomTrainer);

    @Update
    void update(RoomTrainer... roomTrainers);

    @Update
    void update(List<RoomTrainer> roomTrainerList);

    @Delete
    void delete(RoomTrainer roomTrainer);

    @Delete
    void delete(RoomTrainer... roomTrainers);

    @Delete
    void delete(List<RoomTrainer> roomTrainerList);

    @Query("SELECT * FROM RoomTrainer rt where rt.id = :id")
    RoomTrainer findById(Long id);

    @Query("SELECT * FROM RoomTrainer")
    List<RoomTrainer> findAll();

    @Query("DELETE FROM RoomTrainer")
    void deleteAll();
}
