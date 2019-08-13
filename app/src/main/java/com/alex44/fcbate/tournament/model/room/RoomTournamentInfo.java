package com.alex44.fcbate.tournament.model.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoomTournamentInfo {

    @NonNull
    @PrimaryKey
    private Long position;

    private String teamName;

    private Long games;

    private Long wins;

    private Long draws;

    private Long loses;

    private String diffs;

    private Long points;

}
