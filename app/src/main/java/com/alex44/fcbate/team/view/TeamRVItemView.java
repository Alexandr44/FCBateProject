package com.alex44.fcbate.team.view;

public interface TeamRVItemView {

    int getPos();

    void setPhoto(String url);

    void setFirstName(String firstName);

    void setLastName(String lastName, boolean isPlayer);

    void setInfo(String info, boolean isPlayer);

}
