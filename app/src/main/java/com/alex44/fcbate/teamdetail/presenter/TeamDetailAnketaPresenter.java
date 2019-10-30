package com.alex44.fcbate.teamdetail.presenter;

import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.view.TeamDetailAnketaView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class TeamDetailAnketaPresenter extends MvpPresenter<TeamDetailAnketaView> {

    private TeamDetailDTO teamDetailData;

    public TeamDetailAnketaPresenter() {}

    public void setData(TeamDetailDTO teamDetailDTO) {
        this.teamDetailData = teamDetailDTO;
    }

    protected void updateAnketaText() {
        getViewState().setAnketaText(teamDetailData.getAnketa());
    }

}
