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
public class RoomTeamDetailStatistic {

    @NonNull
    @PrimaryKey //<playerId>_year
    private String itemId;

    private Long playerId;

    private int matches;

    private Long minutes;

    private int goals;

    private int goalPasses;

    private int yellowCards;

    private int redCards;

    private int dryMatches;

    private int missedGoals;

    private int year;

}
