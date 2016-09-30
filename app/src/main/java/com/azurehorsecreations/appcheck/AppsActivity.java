package com.azurehorsecreations.appcheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppsActivity extends AppCompatActivity {
    private static final String TAG = "AppsActivity";
    private static final String APP_NAME = "app-debug";
    Button installGoodAppButton;
    Button checkAppsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        checkAppsButton = (Button)findViewById(R.id.check_apps_button);
        checkAppsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppInfoActivity.class);
                startActivity(intent);
            }
        });
        installGoodAppButton = (Button)findViewById(R.id.install_app_button);
        installGoodAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppHelper.installAppFromDownload(getApplication(), "GoodApp");
            }
        });
    }
}
