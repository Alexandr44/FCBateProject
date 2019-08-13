package com.alex44.fcbate.home.model.dto;

import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
public class MatchesListResponse {

    @Expose
    @SerializedName("games")
    private List<MatchDTO> list;

    @Expose
    @SerializedName("current")
    private int currentMatch;
}
