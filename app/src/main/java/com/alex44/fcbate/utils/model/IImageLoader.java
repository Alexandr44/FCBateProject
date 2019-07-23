package com.alex44.fcbate.utils.model;

public interface IImageLoader<T> {
    void loadInto(String url, T container, int corners);
}
