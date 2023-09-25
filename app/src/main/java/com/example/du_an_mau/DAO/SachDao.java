package com.example.du_an_mau.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.du_an_mau.database.DBHelper;
import com.example.du_an_mau.model.loaisach;
import com.example.du_an_mau.model.sach;

import java.util.ArrayList;

public class SachDao {
    Context context;
    DBHelper dbHelper;

    public SachDao(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public ArrayList<sach> getData(){
        ArrayList<sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("select * from sach", null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    list.add(new sach(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getDouble(2),
                            cursor.getInt(3),
                            cursor.getString(4)
                    ));
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG,"Lỗi", e);
        }
        return list;
    }

    public void themSach(sach sc){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("masach", sc.getMasach());
        values.put("tensach", sc.getTensach());
        values.put("giathue", sc.getGiaThue());
        values.put("maloai", sc.getMaloai());

        long check = db.insert("sach", null, values);

        if (check > 0) {
            Toast.makeText(context, "Thêm thành công",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Thêm thất bại",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void suaSach(sach sc){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("tensach", sc.getTensach());
        values.put("giathue", sc.getGiaThue());
        values.put("maloai", sc.getMaloai());

        long check = db.update("sach", values, "masach =?", new String[]{String.valueOf(sc.getMasach())});

        if (check > 0)
            Toast.makeText(context, "Sửa thành công",
                    Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Sửa thất bại",
                Toast.LENGTH_SHORT).show();
    }

    public void xoaSach(int masach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        long check = db.delete("sach", "masach = ?", new String[]{String.valueOf(masach)});

        if (check > 0){
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
