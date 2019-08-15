package com.alex44.fcbate.calendar.model.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(entity = RoomTeam.class, parentColumns = "id", childColumns = "leftTeamId", onDelete = CASCADE),
        @ForeignKey(entity = RoomTeam.class, parentColumns = "id", childColumns = "rightTeamId", onDelete = CASCADE),
        @ForeignKey(entity = RoomTournament.class, parentColumns = "id", childColumns = "tournamentId", onDelete = CASCADE)},
        indices = {@Index("leftTeamId"), @Index("rightTeamId"), @Index("tournamentId")})
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoomMatch {

    @NonNull
    @PrimaryKey
    private Long id;

    private Long leftTeamId;

    private Long rightTeamId;

    private Long tournamentId;

    private Long date;

    private int goalsLeft;

    private int goalsRight;

    private boolean isOnline;

}
