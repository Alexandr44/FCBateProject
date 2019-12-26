package com.alex44.fcbate.match.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.match.presenter.MatchOnlinePresenter;
import com.alex44.fcbate.match.view.MatchOnlineRVItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

public class MatchOnlineRVAdapter extends RecyclerView.Adapter<MatchOnlineRVAdapter.ViewHolder> {

    private MatchOnlinePresenter presenter;

    public MatchOnlineRVAdapter(MatchOnlinePresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_match_online_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPos(position);
        presenter.bind(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getMessages().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements MatchOnlineRVItemView {

        @Setter
        private int pos;

        private View view;

        @BindView(R.id.match_online_rv_item_time)
        protected TextView timeView;
        @BindView(R.id.match_online_rv_item_text)
        protected TextView textView;

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
        public void setTime(String time) {
            timeView.setText(time);
        }

        @Override
        public void setText(String text) {
            textView.setText(text);
        }

    }
}
