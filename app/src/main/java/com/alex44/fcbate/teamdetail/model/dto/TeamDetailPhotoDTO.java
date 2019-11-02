package com.alex44.fcbate.teamdetail.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TeamDetailPhotoDTO {

    @Expose
    @SerializedName("photo_file_id")
    private Long id;

    @Expose
    @SerializedName("photo_big")
    private String bigPhotoUrl;

    @Expose
    @SerializedName("photo_small")
    private String smallPhotoUrl;

    @Expose
    @SerializedName("title")
    private String title;

}
