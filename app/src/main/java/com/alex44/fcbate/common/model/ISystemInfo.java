package com.alex44.fcbate.common.model;

public interface ISystemInfo {

    int getBuildVersion();

    boolean needToConfigSSL();

    boolean isStreamApiAvailable();
}
