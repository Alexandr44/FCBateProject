package com.alex44.fcbate.news.model.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomPress {

    @NonNull
    @PrimaryKey
    private Long id;

    private String photoUrl;

    private Long created;

    private String title;

    private String brief;

}
