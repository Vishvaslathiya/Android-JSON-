package com.example.storageexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DbManager {
    private DbHelper helper;
    private Context context;
    private SQLiteDatabase db;

    public DbManager(Context c)
    {
        this.context = c;
    }

    public DbManager open() throws SQLException
    {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        helper.close();
    }
    //Add Employee
    public long AddEmployee(String EmpName, String ContactNo)
    {
        ContentValues cv = new ContentValues();
        cv.put(helper.Emp_Name,EmpName);
        cv.put(helper.Contact_No,ContactNo);

        long i = db.insert(helper.TABLE_NAME,null,cv);
        return i;
    }

    //Retrieve list of employee
    public Cursor GetEmployeeList()
    {
        Cursor c = db.query(helper.TABLE_NAME,null,null,null,null,null,null);
       /* ArrayList<String> list = new ArrayList<String>();
        if(c.moveToNext())
        {
            int ID = c.getInt(c.getColumnIndexOrThrow(helper.Emp_ID));
            list.add(ID + ",");
        }*/
        return c;
    }

    public int Update(int EmpID, String EmpName, String ContactNo)
    {
        ContentValues cv = new ContentValues();
        cv.put(helper.Emp_Name,EmpName);
        cv.put(helper.Contact_No,ContactNo);

        int i = db.update(helper.TABLE_NAME,cv,helper.Emp_ID + "=?",
                new String[]{String.valueOf(EmpID)});
        return i;

    }

    public void Delete(int EmpID)
    {
        db.delete(helper.TABLE_NAME,helper.Emp_ID + "=?",
                new String[]{String.valueOf(EmpID)});
    }
}
