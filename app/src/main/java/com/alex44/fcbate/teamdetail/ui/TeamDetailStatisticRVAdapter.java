package com.alex44.fcbate.teamdetail.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailStatisticPresenter;
import com.alex44.fcbate.teamdetail.view.TeamDetailStatisticRVItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

public class TeamDetailStatisticRVAdapter extends RecyclerView.Adapter<TeamDetailStatisticRVAdapter.ViewHolder> {

    private TeamDetailStatisticPresenter presenter;

    public TeamDetailStatisticRVAdapter(TeamDetailStatisticPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_team_detail_stats_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPos(position);
        presenter.bind(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getStatsData().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements TeamDetailStatisticRVItemView {

        @Setter
        private int pos;

        private View view;

        @BindView(R.id.team_detail_statistic_rv_item_year)
        protected TextView yearView;
        @BindView(R.id.team_detail_statistic_rv_item_matches)
        protected TextView matchesView;
        @BindView(R.id.team_detail_statistic_rv_item_minutes)
        protected TextView minutesView;
        @BindView(R.id.team_detail_statistic_rv_item_goals)
        protected TextView goalsView;
        @BindView(R.id.team_detail_statistic_rv_item_goal_passes)
        protected TextView goalPassesView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setYear(int year) {
            yearView.setText(String.valueOf(year));
        }

        @Override
        public void setMatches(int matches) {
            matchesView.setText(String.valueOf(matches));
        }

        @Override
        public void setMinutes(Long minutes) {
            minutesView.setText(String.valueOf(minutes));
        }

        @Override
        public void setGoals(int goals) {
            goalsView.setText(String.valueOf(goals));
        }

        @Override
        public void setGoalPasses(int goalPasses) {
            goalPassesView.setText(String.valueOf(goalPasses));
        }
    }
}
