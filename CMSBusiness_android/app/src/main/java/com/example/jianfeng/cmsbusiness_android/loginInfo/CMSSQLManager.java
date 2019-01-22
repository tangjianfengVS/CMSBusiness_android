package com.example.jianfeng.cmsbusiness_android.loginInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.jianfeng.cmsbusiness_android.contacts.CMSContactsVO;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianfeng on 19/1/13.
 */
public class CMSSQLManager extends SQLiteOpenHelper {
    private static String name = "CMSBusinessTest.db";             //库名
    private static String ContactsListTabName = "CMSContactsList"; //联系人表名
    private static int version = 1;
    private Context myContent;

    private static CMSSQLManager instance = null;
    /**
     * integer：整形
     * real：浮点型
     * text：文本类型
     * blob：二进制类型
     * PRIMARY KEY将id列设置为主键
     * AutoIncrement关键字表示id列是自动增长的
     */
    //db.execSQL("create table info (_id integer primary key autoincrement,name varchar(20))");
    //private static final String CREATE_BOOK = "CREATE TABLE book ("
    //        + "id integer PRIMARY KEY Autoincrement ,"
    //        + "author text ,"
    //        + "price real ,"
    //        + "pages integer,"
    //        + "name text )";

    public static CMSSQLManager getInstens(Context context) {
        if (instance == null) {
            instance = new CMSSQLManager(context);
        }
        return instance;
    }

    private CMSSQLManager(Context context) {
        /** 建库 */
        super(context, name, null, version);
        myContent = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** 建表 */
        db.execSQL("create table if not exists " + ContactsListTabName + "(UserID text primary key autoincrement, Contacts blob)");
        Toast.makeText(myContent, "数据库和表创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 更新
     * @param contacts
     */
    public void updateContacts(List<CMSContactsVO> contacts) {
        for (int i = 0; i < contacts.size(); i++) {

        }
    }

    /**
     * 保存 ， 插入
     * @param contacts
     */
    public void saveContacts(List<CMSContactsVO> contacts) {

        try {
            SQLiteDatabase database = instance.getWritableDatabase();

            for(CMSContactsVO VO:contacts){
                String userID = VO.UserID;
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
                objectOutputStream.writeObject(VO);
                objectOutputStream.flush();
                byte data[] = arrayOutputStream.toByteArray();
                objectOutputStream.close();
                arrayOutputStream.close();

                Object obj = new Object[] { data };
                String sql = "insert into " + ContactsListTabName + " (UserID,Contacts) values('" + userID + "'," + obj+")";
                database.execSQL(sql);
            }

            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库查询
     * @param
     */
    public void queryDb() {
        Cursor cursor = instance.getWritableDatabase().rawQuery("select * from " + ContactsListTabName, null);
        while (cursor.moveToNext()) {
            String contactsList = cursor.getString(cursor.getColumnIndex("ContactsList"));

            System.out.print(contactsList);
        }
    }
}
