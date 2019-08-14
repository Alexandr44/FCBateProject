package com.alex44.fcbate.calendar.view;

import org.jetbrains.annotations.NotNull;

public interface CalendarItemView {

    int getPos();

    void setLeftLogo(@NotNull String logoUrl);

    void setRightLogo(@NotNull String logoUrl);

    void setLeftTeamTitle(@NotNull String title);

    void setRightTeamTitle(@NotNull String title);

    void setChampTitle(@NotNull String title);

    void setScore(@NotNull String score);

    void setDate(@NotNull String date);

    void setTime(@NotNull String time);

    void setTournamentLogo(@NotNull String tournamentShortName);

}
