package com.alex44.fcbate.newsdetail.model.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RoomPressDetail {

    @NonNull
    @PrimaryKey
    private Long id;

    private String photoUrl;

    private String dateCreated;

    private String title;

    private String brief;

    private String content;

}
