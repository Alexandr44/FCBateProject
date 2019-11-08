package com.alex44.fcbate.teamdetail.model.room;

import androidx.annotation.NonNull;
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
public class RoomTeamDetail {

    @NonNull
    @PrimaryKey //<type>_<id>
    private String detailId;

    private Long id;

    private String photoUrl;

    private String amplua;

    private String post;

    private String title;

    private int playerNumber;

    private String citizenship;

    private int age;

    private String firstName;

    private String lastName;

    private String shortName;

    private String bornDate;

    private String bornPlace;

    private String content;

    private String biography;

    private String anketa;

}
