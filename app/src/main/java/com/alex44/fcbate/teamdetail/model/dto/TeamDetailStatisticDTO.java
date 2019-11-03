package com.alex44.fcbate.teamdetail.model.dto;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class TeamDetailStatisticDTO {

    @Expose
    private int matches;

    @Expose
    private Long minutes;

    @Expose
    private int goals;

    @Expose
    private int goalPasses;

    @Expose
    private int yellowCards;

    @Expose
    private int redCards;

    @Expose
    private int dryMatches;

    @Expose
    private int missedGoals;

    @Expose
    private int year;
}
