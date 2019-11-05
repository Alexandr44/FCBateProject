package com.alex44.fcbate.about.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AboutDTO {

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("content")
    private String content;
}
