package com.revesoft.checksignature;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mJavaText;
    private TextView mJniText;

    private native String getToken();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJavaText = (TextView) findViewById(R.id.java_sign);
        mJniText = (TextView) findViewById(R.id.jni_sign);

        try {
            mJavaText.setText("Signature get from java (md5) : " + loadSignatureMd5ByJava());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mJavaText.setText("PackageName not found.");
        }

        System.loadLibrary("CheckSignature");

        mJniText.setText("Get token from jni: " + getToken());

    }
    private String loadSignatureMd5ByJava() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        Log.i("Nasir","Key from java: "+MD5Utils.md5(packageInfo.signatures[0].toCharsString()));
        return MD5Utils.md5(packageInfo.signatures[0].toCharsString());
    }
}
