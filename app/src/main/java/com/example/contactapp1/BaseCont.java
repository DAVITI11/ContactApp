package com.example.contactapp1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BaseCont extends SQLiteOpenHelper {
    String name;
    String tel;
    String sql;
    Cursor cursor;
    private static final String DATABASE_NAME = "ContactAppBase.db";
    private static final int DATABASE_VERSION = 1;
    public BaseCont(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public BaseCont(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql="CREATE TABLE Contacts(Name TEXT,Tel TEXT)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void AddContact(String name, String tel){
        SQLiteDatabase db=this.getWritableDatabase();
        sql="INSERT INTO Contacts(Name,Tel) VALUES('"+name+"','"+tel+"')";
        db.execSQL(sql);
        db.close();
    }
    public void DeleteContact(String name, String tel){
        SQLiteDatabase db=this.getWritableDatabase();
        sql="DELETE FROM Contacts WHERE Name='"+name+"'" + "AND Tel='"+tel+"'";
        db.execSQL(sql);
        db.close();
    }
    public List<User> GetContacts(){
        SQLiteDatabase db=this.getReadableDatabase();
        sql="SELECT * FROM Contacts";
        cursor=db.rawQuery(sql,null);
        ArrayList<User> arrayList=new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                name=cursor.getString(0);
                tel=cursor.getString(1);
                arrayList.add(new User(name,tel));
            }while(cursor.moveToNext());
        }
        return arrayList;
    }
    public List<User> SearchContact(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        sql = "SELECT * FROM Contacts WHERE Name LIKE '%" + name + "%'";
        cursor = db.rawQuery(sql, null);
        ArrayList<User> arrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name1 = cursor.getString(0);
                String tel = cursor.getString(1);
                arrayList.add(new User(name1, tel));
            } while (cursor.moveToNext());
        }
        return arrayList;
    }
    public boolean CheckContact(String tel){
        SQLiteDatabase db=this.getReadableDatabase();
        sql="SELECT * FROM Contacts WHERE Tel='"+tel+"'";
        cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }
}
