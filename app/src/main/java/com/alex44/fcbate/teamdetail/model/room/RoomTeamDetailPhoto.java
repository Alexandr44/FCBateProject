package com.alex44.fcbate.teamdetail.model.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomTeamDetailPhoto {

    @NonNull
    @PrimaryKey //<type>_<id>
    private String photoId;

    private String memberId;

    private Long id;

    private String bigPhotoUrl;

    private String smallPhotoUrl;

    private String title;

}
