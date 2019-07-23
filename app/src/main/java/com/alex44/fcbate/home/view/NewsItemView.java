package com.alex44.fcbate.home.view;

import org.jetbrains.annotations.NotNull;

public interface NewsItemView {

    int getPos();

    void setPhoto(@NotNull String photoUrl);

    void setTitle(@NotNull String title);

    void setDateTime(@NotNull String dateTime, boolean isInLastHour);

}
