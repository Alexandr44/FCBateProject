package com.alex44.fcbate.teamdetail.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoDTO {

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
