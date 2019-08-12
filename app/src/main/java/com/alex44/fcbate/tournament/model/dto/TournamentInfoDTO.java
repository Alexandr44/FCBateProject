package com.alex44.fcbate.tournament.model.dto;

import com.google.gson.annotations.Expose;

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
public class TournamentInfoDTO {

    @Expose
    private Long position;

    @Expose
    private String teamName;

    @Expose
    private Long games;

    @Expose
    private Long wins;

    @Expose
    private Long draws;

    @Expose
    private Long loses;

    @Expose
    private String diffs;

    @Expose
    private Long points;

}
