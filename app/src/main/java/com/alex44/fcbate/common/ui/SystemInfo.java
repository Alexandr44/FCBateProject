package com.alex44.fcbate.common.ui;

import android.os.Build;

import com.alex44.fcbate.common.model.ISystemInfo;

public class SystemInfo implements ISystemInfo {

    @Override
    public int getBuildVersion() {
        return Build.VERSION.SDK_INT;
    }

    @Override
    public boolean needToConfigSSL() {
        return getBuildVersion() < 22;
    }

    @Override
    public boolean isStreamApiAvailable() {
        return getBuildVersion() >= 24;
    }

}
