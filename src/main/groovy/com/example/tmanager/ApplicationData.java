package com.example.tmanager;

public class ApplicationData {
    private String AppName;
    private int AppMemory;

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public int getAppMemory() {
        return AppMemory;
    }

    public void setAppMemory(int appMemory) {
        AppMemory = appMemory;
    }

    public ApplicationData(String appName, int appMemory) {
        AppName = appName;
        AppMemory = appMemory;
    }
}
