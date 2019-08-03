package com.alex44.fcbate.common.model;

public interface INetworkStatus {
    enum Status {
        WIFI,
        MOBILE,
        ETHERNET,
        OTHER,
        OFFLINE,
    }

    Status getStatus();

    boolean isOnline();

    boolean isWifi();

    boolean isEthernet();

    boolean isMobile();

    boolean isOffline();
}