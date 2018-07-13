package com.example.hamed.newdatabase;

/**
 * Created by hamed on 7/6/18.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AppDataBase {

    private SQLiteDatabase sqlDb;
    private Context context;
    private AppDataBaseHelper dbHelper;

    private static final String DataBase_Name = "SMS.Db";
    private static final int DataBase_version = 4;
    private static final String Table_Devices = "Devices";
    private static final String Column_Id = "_Id";

    //Device Column
    private static final String Column_DeviceName = "DeviceName";
    private static final String Column_DeviceStatus = "DeviceStatus";
    private static final String Column_DeviceState = "DeviceState";
    private static final String Column_DeviceSimCard = "DeviceSimCard";
    private static final String Column_DeviceNeedResponse = "needResponse";
    private static final String Column_DeviceLastUpdate = "lastUpdate";


    private String[] allColumnsForDevicesTable = {Column_Id, Column_DeviceName
            , Column_DeviceStatus, Column_DeviceState, Column_DeviceLastUpdate
            , Column_DeviceSimCard, Column_DeviceNeedResponse};


    static final String Create_Table_Devices = "create table " + Table_Devices + " ( "
            + Column_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_DeviceName + " TEXT, "
            + Column_DeviceStatus + " INTEGER, "
            + Column_DeviceState + " INTEGER, "
            + Column_DeviceLastUpdate + " INTEGER, "
            + Column_DeviceSimCard + " TEXT, "
            + Column_DeviceNeedResponse + " INTEGER );";

    public AppDataBase(Context ctx) {
        context = ctx;
    }

    public AppDataBase open() throws android.database.SQLException {
        dbHelper = new AppDataBaseHelper(context);
        sqlDb = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long addDevice(DeviceModel model) {
        ContentValues values = new ContentValues();
        values.put(Column_DeviceName, model.getDeviceName());
        values.put(Column_DeviceSimCard, model.getSimCard());
        values.put(Column_DeviceStatus, model.getStatus());
        values.put(Column_DeviceState, model.getState());
        values.put(Column_DeviceLastUpdate, 0);
        values.put(Column_DeviceNeedResponse, model.getNeedResponse());
        return sqlDb.insert(Table_Devices, null, values);
    }


    public ArrayList<DeviceModel> getAllDevice() {
        try {
            ArrayList<DeviceModel> models = new ArrayList<>();
            Cursor cursor = sqlDb.query(Table_Devices, allColumnsForDevicesTable, null, null, null, null, null);
            for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious())
                models.add(cursorToDevice(cursor));

            cursor.close();
            return models;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    public DeviceModel getDevice(String Number) {

        Number = Number.replace("+98", "0");

        Cursor cursor = sqlDb.query(Table_Devices,
                allColumnsForDevicesTable,
                Column_DeviceSimCard + "=?",
                new String[]{String.valueOf(Number)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        DeviceModel model = null;
        if (cursor != null) {

            model = cursorToDevice(cursor);
            cursor.close();
        }


        return model;
    }


    public void deleteDevice(DeviceModel deviceModel) {
        sqlDb.delete(Table_Devices, Column_Id + " =?",
                new String[]{String.valueOf(deviceModel.getID())});
        sqlDb.close();
    }


    public int updateDevice(DeviceModel model) {


        ContentValues values = new ContentValues();
        values.put(Column_DeviceName, model.getDeviceName());
        values.put(Column_DeviceStatus, model.getStatus());
        values.put(Column_DeviceSimCard, model.getSimCard());
        values.put(Column_DeviceState, model.getState());
        values.put(Column_DeviceNeedResponse, model.getNeedResponse());
        values.put(Column_DeviceLastUpdate, System.currentTimeMillis());

        // updating row
        return sqlDb.update(Table_Devices, values, Column_Id + " =?",
                new String[]{String.valueOf(model.getID())});
    }


    private DeviceModel cursorToDevice(Cursor cursor) {
        DeviceModel deviceModel = new DeviceModel();

        deviceModel.setDeviceName(cursor.getString(cursor.getColumnIndex(Column_DeviceName)));
        deviceModel.setStatus(cursor.getInt(cursor.getColumnIndex(Column_DeviceStatus)));
        deviceModel.setState(cursor.getInt(cursor.getColumnIndex(Column_DeviceState)));
        deviceModel.setID(cursor.getInt(cursor.getColumnIndex(Column_Id)));
        deviceModel.setNeedResponse(cursor.getInt(cursor.getColumnIndex(Column_DeviceNeedResponse)));
        deviceModel.setLastUpdate(cursor.getLong(cursor.getColumnIndex(Column_DeviceLastUpdate)));
        deviceModel.setSimCard(cursor.getString(cursor.getColumnIndex(Column_DeviceSimCard)));

        return deviceModel;

    }


    private static class AppDataBaseHelper extends SQLiteOpenHelper {

        AppDataBaseHelper(Context ctx) {
            super(ctx, DataBase_Name, null, DataBase_version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Create_Table_Devices);

        }

        public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
            db.execSQL(" Drop Table If Exists " + Table_Devices);
            onCreate(db);
        }
    }

}
