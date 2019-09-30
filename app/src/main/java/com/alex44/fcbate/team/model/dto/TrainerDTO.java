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
public class TrainerDTO implements Serializable {

    @Expose
    @SerializedName(value = "trainer_general_id")
    private Long id;

    @Expose
    @SerializedName("photo")
    private String photoUrl;

    @Expose
    @SerializedName("age")
    private int age;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("post")
    private String post;

    @Expose
    @SerializedName("born_date")
    private String bornDate;

}
