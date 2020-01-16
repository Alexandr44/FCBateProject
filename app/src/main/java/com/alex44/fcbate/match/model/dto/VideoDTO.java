package com.alex44.fcbate.match.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoDTO {

    @Expose
    @SerializedName("video_file_id")
    private Long id;

    @Expose
    @SerializedName("image")
    private String imageUrl;

    @Expose
    @SerializedName("youtube_link")
    private String youtubeLink;

    @Expose
    @SerializedName("is_youtube")
    private boolean youtube;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("video")
    private String video;

}
