package com.alex44.fcbate.news.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationDTO implements Serializable {

    @Expose
    @SerializedName("declaration_general_id")
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
