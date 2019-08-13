package com.alex44.fcbate.tournament.view;

public interface TournamentRVItemView {

    int getPos();

    void setPosition(String pos);

    void setTeam(String team);

    void setGames(String games);

    void setDiffs(String diffs);

    void setPoints(String points);

    void highlightBackground();
}
