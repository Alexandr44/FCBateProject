package com.alex44.fcbate.calendar.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.calendar.presenter.CalendarPresenter;
import com.alex44.fcbate.calendar.view.CalendarItemView;
import com.alex44.fcbate.calendar.view.CalendarSubtitleView;
import com.alex44.fcbate.common.model.IImageLoader;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

public class CalendarRVAdapter extends RecyclerView.Adapter {

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;


    private CalendarPresenter presenter;

    public CalendarRVAdapter(CalendarPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MatchDTO.DATA_ROW) {
            return new ItemHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_match_item, parent, false));
        }
        else {
            return new SubtitleHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.title_row, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (presenter.getData().get(position).getDataType() == MatchDTO.DATA_ROW) {
            final ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.setPos(position);
            presenter.bind(itemHolder);
        }
        else {
            final SubtitleHolder subtitleHolder = (SubtitleHolder) holder;
            subtitleHolder.setPos(position);
            presenter.bind(subtitleHolder);
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getData().size();
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getData().get(position).getDataType();
    }

    protected class ItemHolder extends RecyclerView.ViewHolder implements CalendarItemView {

        @Setter
        private int pos;

        @BindView(R.id.left_logo)
        protected ImageView leftLogo;
        @BindView(R.id.right_logo)
        protected ImageView rightLogo;
        @BindView(R.id.tournament_logo)
        protected ImageView tournamentLogo;
        @BindView(R.id.left_title)
        protected TextView leftTitle;
        @BindView(R.id.right_title)
        protected TextView rightTitle;
        @BindView(R.id.champ_name)
        protected TextView champName;
        @BindView(R.id.match_date)
        protected TextView matchDate;
        @BindView(R.id.match_time)
        protected TextView matchTime;
        @BindView(R.id.match_score)
        protected TextView matchScore;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setLeftLogo(@NotNull String logoUrl) {
            imageLoader.loadInto(logoUrl, leftLogo, 50);
        }

        @Override
        public void setRightLogo(@NotNull String logoUrl) {
            imageLoader.loadInto(logoUrl, rightLogo, 50);
        }

        @Override
        public void setLeftTeamTitle(@NotNull String title) {
            leftTitle.setText(title);
        }

        @Override
        public void setRightTeamTitle(@NotNull String title) {
            rightTitle.setText(title);
        }

        @Override
        public void setChampTitle(@NotNull String title) {
            champName.setText(title);
        }

        @Override
        public void setScore(@NotNull String score) {
            matchScore.setText(score);
        }

        @Override
        public void setDate(@NotNull String date) {
            matchDate.setText(date);
        }

        @Override
        public void setTime(@NotNull String time) {
            matchTime.setText(time);
        }

        @Override
        public void setTournamentLogo(@NotNull String tournamentShortName) {
            tournamentLogo.setImageResource(getImageResByTournamentName(tournamentShortName));
        }

        private int getImageResByTournamentName(String name) {
            if ("ЧБ".equals(name)) return R.drawable.trnm_bel_league_logo;
            if ("КБ".equals(name)) return R.drawable.trnm_bel_cup_logo;
            if ("СКБ".equals(name)) return R.drawable.trnm_bel_super_cup_logo;
            if ("ЛЧ".equals(name)) return R.drawable.trnm_eur_champion_league_logo;
            if ("ЛЕ".equals(name)) return R.drawable.trnm_eur_league_logo;
            if ("ЛЮ".equals(name)) return R.drawable.trnm_eur_youth_league_logo;
            return 0;
        }
    }

    protected class SubtitleHolder extends RecyclerView.ViewHolder implements CalendarSubtitleView {

        @Setter
        private int pos;

        @BindView(R.id.title_text)
        protected TextView title;

        public SubtitleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setTitle(String title) {
            this.title.setText(title);
        }
    }


}
