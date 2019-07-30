package com.alex44.fcbate.home.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
public class TournamentDTO {

    @Expose
    @SerializedName("tournament_id")
    private Long id;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("title_short")
    private String titleShort;
}
