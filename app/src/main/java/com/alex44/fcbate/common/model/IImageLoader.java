package com.alex44.fcbate.common.model;

public interface IImageLoader<T> {
    void loadInto(String url, T container, int corners);
}
