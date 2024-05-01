package com.example.storageexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {
    Button btnAdd;
    ListView lstEmp;
    DbManager db;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        ControlInitialization();
        ButtonClicks();
        helper = new DbHelper(this);
        db = new DbManager(this);
        db.open();
        GetList();
    }

    private void ButtonClicks() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddRecordActivity.class);
                startActivity(i);
            }
        });
    }

    private void GetList()
    {
        Cursor c = db.GetEmployeeList();
        String[] from ={helper.Emp_ID,helper.Emp_Name,helper.Contact_No};
        int[] to = {R.id.lblID,R.id.lblEmpName,R.id.lblContactNo};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.custom_list,
                c,from,to);
        lstEmp.setEmptyView(findViewById(R.id.empty));
        adapter.notifyDataSetChanged();
        lstEmp.setAdapter(adapter);

        lstEmp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView lblID = view.findViewById(R.id.lblID);
                TextView lblEmpName = view.findViewById(R.id.lblEmpName);
                TextView lblContactNo = view.findViewById(R.id.lblContactNo);

                Intent i = new Intent(getApplicationContext(), AddRecordActivity.class);
                i.putExtra("ID",lblID.getText().toString());
                i.putExtra("Name",lblEmpName.getText().toString());
                i.putExtra("ContactNo",lblContactNo.getText().toString());
                startActivity(i);
            }
        });
    }
    private void ControlInitialization() {
        btnAdd = findViewById(R.id.btnAdd);
        lstEmp = findViewById(R.id.lstEmp);
    }
}