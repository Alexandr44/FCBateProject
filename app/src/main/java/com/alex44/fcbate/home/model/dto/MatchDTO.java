package com.alex44.fcbate.home.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class MatchDTO {

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

}
