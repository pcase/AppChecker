package com.azurehorsecreations.appcheck;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class AppInfoActivity extends AppCompatActivity {
    private static final String TAG = "AppInfoActivity";
    private List<PackageInfo> packageInfoList;
    private List<App> appList;
    private PackageManager packageManager;
    InstallationReceiver receiver;
    private HashSet<String> permissionSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        RecyclerView dataView = (RecyclerView) findViewById(R.id.data_list);
        dataView.addItemDecoration(new SimpleDividerItemDecoration(this));
        packageManager = getPackageManager();
        packageInfoList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        appList = createAppList();
        AppAdapter appAdapter = new AppAdapter(this, appList);
        dataView.setAdapter(appAdapter);
        dataView.setLayoutManager(new LinearLayoutManager(this));
        receiver = new InstallationReceiver();
    }

    private ArrayList<App> createAppList() {
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
}
