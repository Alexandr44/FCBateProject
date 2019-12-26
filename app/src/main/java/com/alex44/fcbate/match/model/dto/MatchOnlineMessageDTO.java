package com.alex44.fcbate.match.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchOnlineMessageDTO implements Serializable {

    @Expose
    @SerializedName("comment")
    private String comment;

    @Expose
    @SerializedName("game_time")
    private String gameTime;

    @Expose
    @SerializedName("created")
    private String created;

}
