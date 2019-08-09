package com.alex44.fcbate.newsdetail.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class NewsDetailDTO {

    @Expose
    @SerializedName("news_general_id")
    private Long id;

    @Expose
    @SerializedName("photo")
    private String photoUrl;

    @Expose
    @SerializedName("created")
    private String dateCreated;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("brief")
    private String brief;

    @Expose
    @SerializedName("content")
    private String content;
}
