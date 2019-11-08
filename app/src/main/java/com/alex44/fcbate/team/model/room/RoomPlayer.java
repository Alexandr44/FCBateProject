package com.alex44.fcbate.team.model.room;

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
public class RoomPlayer {

    @PrimaryKey
    private Long id;

    private String photoUrl;

    private String amplua;

    private int playerNumber;

    private int age;

    private String firstName;

    private String lastName;

    private String shortName;

    private String bornDate;

    private String bornPlace;

}
