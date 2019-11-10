package com.alex44.fcbate.tournament.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.tournament.presenter.TournamentPresenter;
import com.alex44.fcbate.tournament.view.TournamentRVItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

public class TournamentRVAdapter extends RecyclerView.Adapter<TournamentRVAdapter.ViewHolder> {
    private final static String TITLE_TO_HIGHLIGHT = "БАТЭ";

    private TournamentPresenter presenter;

    public TournamentRVAdapter(TournamentPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPos(position);
        presenter.bind(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getData().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements TournamentRVItemView {

        @Setter
        private int pos;

        private View view;

        @BindView(R.id.tournament_rv_pos)
        protected TextView tablePos;
        @BindView(R.id.tournament_rv_team)
        protected TextView tableTeam;
        @BindView(R.id.tournament_rv_games)
        protected TextView tableGames;
        @BindView(R.id.tournament_rv_diffs)
        protected TextView tableDiffs;
        @BindView(R.id.tournament_rv_points)
        protected TextView tablePoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setPosition(String pos) {
            tablePos.setText(pos);
        }

        @Override
        public void setTeam(String team) {
            tableTeam.setText(team);
            if (team.equals(TITLE_TO_HIGHLIGHT)) {
                tableTeam.setTextColor(view.getResources().getColor(R.color.defaultYellow));
            }
        }

        @Override
        public void setGames(String games) {
            tableGames.setText(games);
        }

        @Override
        public void setDiffs(String diffs) {
            tableDiffs.setText(diffs);
        }

        @Override
        public void setPoints(String points) {
            tablePoints.setText(points);
        }

        @Override
        public void highlightBackground() {
            view.setBackground(view.getResources().getDrawable(R.drawable.home_table_background));
        }

    }

}
