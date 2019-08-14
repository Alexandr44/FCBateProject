package com.alex44.fcbate.calendar.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MatchDTO implements Serializable {
    public static final int DATA_ROW = 0;
    public static final int SUBTITLE_ROW = 1;

    @Expose
    @SerializedName("game_general_id")
    private Long id;

    @Expose
    @SerializedName("team1")
    private TeamDTO leftTeam;

    @Expose
    @SerializedName("team2")
    private TeamDTO rightTeam;

    @Expose
    @SerializedName("tournament")
    private TournamentDTO tournament;

    @Expose
    @SerializedName("game_date")
    private String dateStr;

    @Expose
    @SerializedName("goals1")
    private int goalsLeft;

    @Expose
    @SerializedName("goals2")
    private int goalsRight;

    @Expose
    @SerializedName("online")
    private boolean isOnline;

    private int dataType = DATA_ROW;

    public MatchDTO(Long id, TeamDTO leftTeam, TeamDTO rightTeam, TournamentDTO tournament, String dateStr, int goalsLeft, int goalsRight, boolean isOnline) {
        this.id = id;
        this.leftTeam = leftTeam;
        this.rightTeam = rightTeam;
        this.tournament = tournament;
        this.dateStr = dateStr;
        this.goalsLeft = goalsLeft;
        this.goalsRight = goalsRight;
        this.isOnline = isOnline;
        this.dataType = DATA_ROW;
    }

    public MatchDTO(String dateStr) {
        this.dateStr = dateStr;
        this.dataType = SUBTITLE_ROW;
    }
}
