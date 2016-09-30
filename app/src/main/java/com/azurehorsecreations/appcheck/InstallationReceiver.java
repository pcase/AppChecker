package com.azurehorsecreations.appcheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class InstallationReceiver extends BroadcastReceiver {
    public InstallationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getEncodedSchemeSpecificPart();
        App app = AppHelper.getAppFromInstalledList(context, packageName);
        Toast.makeText(context, app.getName() + " was installed", Toast.LENGTH_LONG).show();
        if (AppHelper.isAppSuspicious(app)) {
            AppHelper.uninstallApp(context, packageName);
        }
    }
}
