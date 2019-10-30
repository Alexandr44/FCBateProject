package com.alex44.fcbate.teamdetail.presenter;

import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.view.TeamDetailBiographyView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class TeamDetailBiographyPresenter extends MvpPresenter<TeamDetailBiographyView> {

    private TeamDetailDTO teamDetailData;

    public TeamDetailBiographyPresenter() {}

    public void setData(TeamDetailDTO teamDetailDTO) {
        this.teamDetailData = teamDetailDTO;
    }

    protected void updateBiographyText() {
        getViewState().setBiographyText(teamDetailData.getBiography());
    }

}
