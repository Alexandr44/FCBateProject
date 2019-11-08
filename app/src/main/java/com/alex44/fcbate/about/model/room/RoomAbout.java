package com.alex44.fcbate.about.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomAbout {

    @PrimaryKey
    private int id = 1;

    private String name;

    private String title;

    private String content;

}
