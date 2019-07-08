package com.alex44.fcbate.utils.model;

public interface ISystemInfo {

    int getBuildVersion();

    boolean needToConfigSSL();

    boolean isStreamApiAvailable();
}
