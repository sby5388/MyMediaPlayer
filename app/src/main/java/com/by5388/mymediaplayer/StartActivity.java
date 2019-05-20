package com.by5388.mymediaplayer;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class StartActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PERMISSION = 100;
    public static final int REQUEST_CODE_SETTING = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getPermission();
    }

    private void getPermission() {
        if ((PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) ||
                PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CODE_PERMISSION);
        } else {
            startToMainActivity();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_PERMISSION) {
            boolean getPermission = true;
            for (int result : grantResults) {
                if (result != PERMISSION_GRANTED) {
                    getPermission = false;
                    break;
                }
            }

            if (getPermission) {
                startToMainActivity();
            } else {
                toSetting();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void toSetting() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivityForResult(intent, REQUEST_CODE_SETTING);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SETTING && resultCode == RESULT_OK) {
            getPermission();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
