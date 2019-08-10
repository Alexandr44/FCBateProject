package com.alex44.fcbate.news.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alex44.fcbate.news.model.room.RoomDeclaration;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoomDeclarationDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomDeclaration roomDeclaration);

    @Insert(onConflict = REPLACE)
    void insert(RoomDeclaration... roomDeclaration);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomDeclaration> roomDeclarationList);

    @Update
    void update(RoomDeclaration roomDeclaration);

    @Update
    void update(RoomDeclaration... roomDeclaration);

    @Update
    void update(List<RoomDeclaration> roomDeclarationList);

    @Delete
    void delete(RoomDeclaration roomDeclaration);

    @Delete
    void delete(RoomDeclaration... roomDeclaration);

    @Delete
    void delete(List<RoomDeclaration> roomDeclarationList);

    @Query("SELECT * FROM RoomDeclaration d ORDER BY d.created desc LIMIT :count")
    List<RoomDeclaration> findLast(int count);

    @Query("SELECT * FROM RoomDeclaration d where d.id = :id LIMIT 1")
    RoomDeclaration findById(Long id);
}
