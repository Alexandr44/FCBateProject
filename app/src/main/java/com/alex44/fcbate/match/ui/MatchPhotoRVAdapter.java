package com.alex44.fcbate.match.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.match.presenter.MatchPhotoPresenter;
import com.alex44.fcbate.match.view.MatchPhotoRVItemView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

public class MatchPhotoRVAdapter extends RecyclerView.Adapter<MatchPhotoRVAdapter.ViewHolder> {

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    private MatchPhotoPresenter presenter;

    public MatchPhotoRVAdapter(MatchPhotoPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_match_photo_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPos(position);
        presenter.bind(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getPhotos().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements MatchPhotoRVItemView {

        @Setter
        private int pos;

        private View view;

        @BindView(R.id.match_photo_rv_item_photo)
        protected ImageView photo;

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

    }
}
