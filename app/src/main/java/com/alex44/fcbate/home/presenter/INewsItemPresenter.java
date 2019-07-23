package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.home.view.NewsItemView;

public interface INewsItemPresenter {

    void update(NewsItemView view);

    void imageClicked(int pos);
}
