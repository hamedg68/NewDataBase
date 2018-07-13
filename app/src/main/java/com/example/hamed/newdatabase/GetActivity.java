package com.example.hamed.newdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GetActivity extends AppCompatActivity {
ArrayList<DeviceModel> deviceModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);


        AppDataBase appDataBase = new AppDataBase(this);
        appDataBase.open();
        deviceModels = appDataBase.getAllDevice();
        appDataBase.close();

        Log.i("infoDatabase",deviceModels.get(0).getDeviceName());
        Log.i("infoDatabase",deviceModels.get(0).getSimCard());

    }
}
