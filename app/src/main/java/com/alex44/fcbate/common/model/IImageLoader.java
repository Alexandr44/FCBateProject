package com.alex44.fcbate.common.model;

public interface IImageLoader<T> {

    void loadInto(String url, T container, int corners);

    void loadInto(String url, T container);

    void loadIntoWithCrop(String url, T container, int corners);

    void loadIntoWithPlaceholder(String url, T container, int photoPlaceholder);
}
