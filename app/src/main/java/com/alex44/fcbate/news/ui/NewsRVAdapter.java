package com.alex44.fcbate.news.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.news.presenter.NewsPagerItemPresenter;
import com.alex44.fcbate.news.view.NewsRVItemView;
import com.jakewharton.rxbinding3.view.RxView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    private NewsPagerItemPresenter presenter;

    public NewsRVAdapter(NewsPagerItemPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPos(position);
        presenter.bind(holder);
        RxView.clicks(holder.itemView).map(o -> holder).subscribe(presenter.getClickSubject());
    }

    @Override
    public int getItemCount() {
        return presenter.getData().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements NewsRVItemView {

        @Setter
        private int pos;

        private View view;

        @BindView(R.id.news_rv_item_photo)
        protected ImageView photo;
        @BindView(R.id.news_rv_item_title)
        protected TextView title;
        @BindView(R.id.news_rv_item_date_time)
        protected TextView dateTime;

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
            imageLoader.loadIntoWithCrop(url, photo, 60);
        }

        @Override
        public void setTitle(String title) {
            this.title.setText(title);
        }

        @Override
        public void setDateTime(String dateTime, boolean isInLastHour) {
            this.dateTime.setText(dateTime);
            if (isInLastHour) {
                this.dateTime.setTextColor(view.getResources().getColor(R.color.defaultBlueFontColor));
                this.dateTime.setBackgroundColor(view.getResources().getColor(R.color.defaultYellow));
            }
            else {
                this.dateTime.setTextColor(Color.WHITE);
                this.dateTime.setBackgroundColor(0x000);
            }
        }

    }
}
