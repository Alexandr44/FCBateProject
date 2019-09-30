package com.alex44.fcbate.team.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO implements Serializable {

    @Expose
    @SerializedName(value = "player_general_id")
    private Long id;

    @Expose
    @SerializedName("photo")
    private String photoUrl;

    @Expose
    @SerializedName("amplua")
    private String amplua;

    @Expose
    @SerializedName("player_number")
    private int playerNumber;

    @Expose
    @SerializedName("age")
    private int age;

    @Expose
    @SerializedName("first_name")
    private String firstName;

    @Expose
    @SerializedName("last_name")
    private String lastName;

    @Expose
    @SerializedName("short_name")
    private String shortName;

    @Expose
    @SerializedName("born_date")
    private String bornDate;

    @Expose
    @SerializedName("born_place")
    private String bornPlace;

}
