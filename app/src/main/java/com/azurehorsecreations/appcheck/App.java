package com.azurehorsecreations.appcheck;

import android.content.pm.PermissionInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by pattycase on 8/27/16.
 */
public class App implements Comparable<App> {
    private String name;
    private Drawable icon;
    private String packageName;
    private String sourceDir;
    private String[] requestedPermissionList;
    private boolean isSuspicious;

    public App() {
        this.requestedPermissionList = new String[0];
        this.isSuspicious = false;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPermissionList() {
        return requestedPermissionList;
    }

    public void setPermissionList(String[] permissionList) {
        this.requestedPermissionList = permissionList;
    }

    public boolean isSuspicious() {
        return isSuspicious;
    }

    public void setSuspicious(boolean suspicious) {
        isSuspicious = suspicious;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public int compareTo(App appCompareTo) {
        return this.name.compareTo(appCompareTo.getName());
    }
}
