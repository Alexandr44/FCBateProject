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
public class NewsDTO implements Serializable {

    @Expose
    @SerializedName("news_general_id")
    private Long id;

    @Expose
    @SerializedName("photo")
    private String photoUrl;

    @Expose
    @SerializedName("created")
    private String created;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("brief")
    private String brief;

}
