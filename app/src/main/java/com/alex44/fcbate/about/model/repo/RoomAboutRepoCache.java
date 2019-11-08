package com.alex44.fcbate.about.model.repo;

import com.alex44.fcbate.about.model.dto.AboutDTO;
import com.alex44.fcbate.about.model.room.RoomAbout;
import com.alex44.fcbate.common.model.db.DatabaseRoom;

public class RoomAboutRepoCache implements IAboutRepoCache {
    private final int ABOUT_ID = 1;

    @Override
    public boolean putAbout(AboutDTO aboutDTO) {
        final RoomAbout roomAbout = new RoomAbout(ABOUT_ID, aboutDTO.getName(), aboutDTO.getTitle(), aboutDTO.getContent());
        DatabaseRoom.getInstance().getAboutDao().insert(roomAbout);
        return true;
    }

    @Override
    public AboutDTO getAbout() {
        final RoomAbout roomAbout = DatabaseRoom.getInstance().getAboutDao().findById(ABOUT_ID);
        return new AboutDTO(roomAbout.getName(), roomAbout.getTitle(), roomAbout.getContent());
    }
}
