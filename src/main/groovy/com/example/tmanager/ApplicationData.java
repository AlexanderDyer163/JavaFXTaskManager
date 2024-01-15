package com.example.tmanager;

public class ApplicationData {
    private String AppName;
    private String AppMemory;

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppMemory() {
        return AppMemory;
    }

    public void setAppMemory(String appMemory) {
        AppMemory = appMemory;
    }

    public ApplicationData(String appName, String appMemory) {
        AppName = appName;
        AppMemory = appMemory;
    }
}
