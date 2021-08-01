package com.yaseen.therevolutionarystep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;


        try {
            info = manager.getPackageInfo(
                    this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        progressDialog = new ProgressDialog(SplashScreen.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        final String version = info.versionName;
        Log.e("version", version);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);










    }
}