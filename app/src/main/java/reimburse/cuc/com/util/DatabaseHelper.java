package reimburse.cuc.com.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.bean.Cost;

/**
 * Created by hp1 on 2017/8/19.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "consumption_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("create table if not exists consumption(" +
                 "id integer primary key," +
                 "consumption_content varchar," +
                 "consumption_date varchar," +
                 "consumption_money varchar," +
                 "consumption_picUris varchar)");
    }

    public void insertConsumption(Consumption bean){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues kv = new ContentValues();
        kv.put("consumption_content",bean.getContent());
        kv.put("consumption_date",bean.getDate());
        kv.put("consumption_money",bean.getMoney());
        kv.put("consumption_picUris",bean.getPicUris());

        database.insert("consumption", null, kv);
    }

    public Cursor getAllConsumptionData(){
        SQLiteDatabase database = getWritableDatabase();
        return  database.query("consumption",null,null,null,null,null,"consumption_date ASC");
    }

    public Cursor getConsumptionsByIds(String[] ids){
        SQLiteDatabase database = getWritableDatabase();
        return   database.query("consumption",null,"id in",ids,null,null,"consumption_date ASC");
    }

    public void deleteAllData(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("consumption",null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
