package com.alex44.fcbate.home.model.dto;

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
public class TeamDTO  implements Serializable {

    @Expose
    @SerializedName("team_general_id")
    private Long id;

    @Expose
    @SerializedName("logo")
    private String logoUrl;

    @Expose
    @SerializedName("title")
    private String title;

}
