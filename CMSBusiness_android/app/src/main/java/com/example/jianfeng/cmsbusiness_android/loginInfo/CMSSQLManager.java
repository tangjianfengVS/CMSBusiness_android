package com.example.jianfeng.cmsbusiness_android.loginInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.jianfeng.cmsbusiness_android.contacts.CMSContactsVO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianfeng on 19/1/13.
 */
public class CMSSQLManager extends SQLiteOpenHelper {
    private static String name = "CMSBusinessTest2.db";             //库名
    private static String ContactsListTabName = "CMSContactsList2"; //联系人表名
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

    private String ContactTabCreateSQL = "create table if not exists " + ContactsListTabName + " (ContactId integer primary key autoincrement, UserID text, Contacts blob)";


    /** --------------------单列的创建-----------------  */
    public static CMSSQLManager shared(Context context) {
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
        /** 建表 not create and not execute if system has tab */
        db.execSQL(ContactTabCreateSQL);
        Toast.makeText(myContent, "数据库和表创建成功", Toast.LENGTH_SHORT).show();
    }

    /*  onUpgrade:数据库版本发生变化时调用该方法,该方法特别适合做表结构的修改
     *  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     *  执行sql语句做表结构的修改   数据库版本也需要同时变更  才会起作用
     *  db.execSQL("alter table user add phone varchar(20)");
     *  System.out.println("旧版本:" + oldVersion + "新版本+" + newVersion);
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * 更新
     * @param contacts
     */
    public void updateContacts(List<CMSContactsVO> contacts) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            for(CMSContactsVO VO:contacts) {
                String userID = VO.UserID;
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
                objectOutputStream.writeObject(VO);
                objectOutputStream.flush();
                byte data[] = arrayOutputStream.toByteArray();
                objectOutputStream.close();
                arrayOutputStream.close();

                Object[] obj = new Object[] { userID, data };
                String sql = "UPDATE " + ContactsListTabName + " SET UserID = ?, Contacts = ? WHERE id IN (SELECT id FROM student ORDER BY id ASC LIMIT 0,1000";
                db.execSQL(sql,obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        db.close(); // 关闭数据库
    }

    /**
     * 保存 ， 插入
     * @param contacts
     */
    public void saveContacts(List<CMSContactsVO> contacts) {

        try {
            SQLiteDatabase database = getWritableDatabase();

            for(CMSContactsVO VO:contacts){
                String userID = VO.UserID;
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
                objectOutputStream.writeObject(VO);
                objectOutputStream.flush();
                byte data[] = arrayOutputStream.toByteArray();
                objectOutputStream.close();
                arrayOutputStream.close();

                Object[] obj = new Object[] { userID, data };
                String sql = "insert into " + ContactsListTabName + "(UserID, Contacts) values(?,?)";
                database.execSQL(sql, obj);
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库查询所以联系人
     * @param
     */
    public List<CMSContactsVO> loadContacts() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<CMSContactsVO> list = new ArrayList<CMSContactsVO>();

        Cursor cursor = database.rawQuery("select * from " + ContactsListTabName, null);

        if (cursor!=null && cursor.getCount()>0) {

            try {
                while (cursor.moveToNext()) {
                    String userId = cursor.getString(cursor.getColumnIndex("UserID"));

                    byte[] contactsByte = cursor.getBlob(cursor.getColumnIndex("Contacts"));
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contactsByte); // 创建ByteArrayInputStream对象
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);  // 创建ObjectInputStream对象
                    CMSContactsVO object = (CMSContactsVO) objectInputStream.readObject();              // 从objectInputStream流中读取一个对象
                    byteArrayInputStream.close();           // 关闭输入流
                    objectInputStream.close();              // 关闭输入流
                    list.add(object);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            database.close();
        }
        return list;
    }
}
