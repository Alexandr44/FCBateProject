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
public class RoomTrainer {

    @PrimaryKey
    private Long id;

    private String photoUrl;

    private int age;

    private String title;

    private String post;

    private String bornDate;

}
