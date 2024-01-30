package com.example.tmanager;

public class ApplicationData {
    private String AppName;
    private int AppMemory;

    public String getAppName() {
        return AppName;
    }

    public int getAppMemory() {
        return AppMemory;
    }

    public ApplicationData(String appName, int appMemory) {
        AppName = appName;
        AppMemory = appMemory;
    }
}
