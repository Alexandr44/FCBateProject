package com.alex44.fcbate.teamdetail.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDetailDTO implements Serializable {

    @Expose
    @SerializedName(value = "player_general_id", alternate = {"trainer_general_id"})
    private Long id;

    @Expose
    @SerializedName("photo")
    private String photoUrl;

    @Expose
    @SerializedName("amplua")
    private String amplua;

    @Expose
    @SerializedName("post") //пост тренера
    private String post;

    @Expose
    @SerializedName("title")    //фио тренера
    private String title;

    @Expose
    @SerializedName("player_number")
    private int playerNumber;

    @Expose
    @SerializedName("citizenship")
    private String citizenship;

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

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("biography")
    private String biography;

    @Expose
    @SerializedName("anketa")
    private String anketa;

}
