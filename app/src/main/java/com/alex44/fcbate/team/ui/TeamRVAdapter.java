package com.alex44.fcbate.team.ui;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.team.presenter.TeamPagerItemPresenter;
import com.alex44.fcbate.team.view.TeamRVItemView;
import com.jakewharton.rxbinding3.view.RxView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class TeamRVAdapter extends RecyclerView.Adapter {
    protected final static int TYPE_DATA = 0;
    protected final static int TYPE_TITLE = 1;

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    private TeamPagerItemPresenter presenter;

    public TeamRVAdapter(TeamPagerItemPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATA) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_team_rv_item, parent, false));
        }
        else {
            return new SubtitleHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.title_row, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (presenter.isTitle(position)) {
            final SubtitleHolder subtitleHolder = (SubtitleHolder)holder;
            subtitleHolder.setPos(position);
            presenter.bindTitle(subtitleHolder);
        }
        else {
            final ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.setPos(position);
            presenter.bind(viewHolder);
            RxView.clicks(holder.itemView).map(o -> viewHolder).subscribe(presenter.getClickSubject());
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.isTitle(position) ? TYPE_TITLE : TYPE_DATA;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements TeamRVItemView {

        @Setter
        private int pos;

        private View view;

        @BindView(R.id.team_rv_item_photo)
        protected ImageView photo;
        @BindView(R.id.news_rv_item_last_name)
        protected TextView lastName;
        @BindView(R.id.news_rv_item_first_name)
        protected TextView firstName;
        @BindView(R.id.news_rv_item_info)
        protected TextView info;

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
        public void setPhoto(String url) {
            imageLoader.loadInto(url, photo);
        }

        @Override
        public void setFirstName(String firstName) {
            this.firstName.setText(firstName);
        }

        @Override
        public void setLastName(String lastName, boolean isPlayer) {
            this.lastName.setText(lastName);
            if (isPlayer) {
                this.info.setTextSize(COMPLEX_UNIT_SP, 17);
            }
            else {
                this.info.setTextSize(COMPLEX_UNIT_SP, 13);
            }
        }

        @Override
        public void setInfo(String info, boolean isPlayer) {
            this.info.setText(info);
            if (isPlayer) {
                this.info.setTextSize(COMPLEX_UNIT_SP, 20);
                this.info.setTypeface(this.info.getTypeface(), Typeface.BOLD);
            }
            else {
                this.info.setTextSize(COMPLEX_UNIT_SP, 12);
                this.info.setTypeface(this.info.getTypeface(), Typeface.NORMAL);
            }
        }

    }

    protected class SubtitleHolder extends RecyclerView.ViewHolder implements TeamRVItemView {

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
        public void setPhoto(String url) {
        }

        @Override
        public void setFirstName(String firstName) {
        }

        @Override
        public void setLastName(String lastName, boolean isPlayer) {
        }

        @Override
        public void setInfo(String info, boolean isPlayer) {
            title.setText(info);
        }

    }
}
