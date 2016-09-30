package com.azurehorsecreations.appcheck;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by pattycase on 9/24/16.
 */
public class AppHelper {

    public static void uninstallApp(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    public static void installAppFromDownload(Context context, String appName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apiUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/apps/" + appName + ".apk"));

        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, apiUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        context.startActivity(intent);
    }

    public static boolean isAppSuspicious(App app) {
        String[] permissionList = app.getPermissionList();
        if (permissionList != null) {
            HashSet<String> suspiciousPermissionSet = createSuspciousPermissionSet();
            int matches = 0;
            for (String permission : permissionList) {
                if (suspiciousPermissionSet.contains(permission)) {
                    ++matches;
                }
            }
            return matches >= suspiciousPermissionSet.size() - 1;
        } else {
            return false;
        }
    }

    public static ArrayList<App> createAppList(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        ArrayList<App> newAppList = new ArrayList<>();
        for (PackageInfo packageInfo : packageInfoList) {
            App app = new App();
            app.setName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            app.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            app.setPackageName(packageInfo.applicationInfo.packageName);
            app.setPermissionList(packageInfo.requestedPermissions);
            if (AppHelper.isAppSuspicious(app)) {
                app.setSuspicious(true);
            }
            newAppList.add(app);
        };
        Collections.sort(newAppList);
        return newAppList;
    }

    public static App getAppFromInstalledList(Context context, String packageName) {
        App appToReturn = null;
        ArrayList<App> appList = createAppList(context);
        for (App app : appList) {
            if (app.getPackageName().equals(packageName)) {
                appToReturn = app;
            }
        }
        return appToReturn;
    }

    private static HashSet<String> createSuspciousPermissionSet() {
        HashSet<String> permissionSet = new HashSet<>();
        permissionSet.add("android.permission.CALL_PHONE");
        permissionSet.add("android.permission.CHANGE_CONFIGURATION");
        permissionSet.add("android.permission.INTERNET");
        permissionSet.add("android.permission.READ_CALL_LOG");
        permissionSet.add("android.permission.READ_CONTACTS");
        permissionSet.add("android.permission.READ_EXTERNAL_STORAGE");
        permissionSet.add("android.permission.SEND_SMS");
        return permissionSet;
    }
}
