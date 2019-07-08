package com.alex44.fcbate.home.view;

import org.jetbrains.annotations.NotNull;

public interface MatchItemView {

    int getPos();

    void setLeftLogo(@NotNull String logoUrl);

    void setRightLogo(@NotNull String logoUrl);

    void setLeftTeamTitle(@NotNull String title);

    void setRightTeamTitle(@NotNull String title);

    void setChampTitle(@NotNull String title);

    void setScore(@NotNull String score);

    void setDate(@NotNull String date);

    void setTime(@NotNull String time);
}
