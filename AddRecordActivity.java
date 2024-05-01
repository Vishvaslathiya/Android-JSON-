package com.example.storageexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecordActivity extends AppCompatActivity {
    EditText txtEmpName, txtContactNo;
    Button btnSave, btnDelete;
    DbManager db;
    int ID =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        ControlInitialization();
        ButtonClicks();
        db = new DbManager(this);
        db.open();

        Intent i = getIntent();
        if(i.getStringExtra("ID") != null)
        {
            ID = Integer.parseInt(i.getStringExtra("ID"));
            String EmpName = i.getStringExtra("Name");
            String ContactNo = i.getStringExtra("ContactNo");

            txtEmpName.setText(EmpName);
            txtContactNo.setText(ContactNo);
            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void ButtonClicks() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Delete(ID);
                BackHome();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmpName = txtEmpName.getText().toString();
                String ContactNo = txtContactNo.getText().toString();
                if(ID == 0) {
                    long i = db.AddEmployee(EmpName, ContactNo);
                    if (i > 0) {
                        Toast.makeText(getApplicationContext(), "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                        BackHome();
                    }
                }else {
                    int i = db.Update(ID,EmpName,ContactNo);
                    if(i>0)
                    {
                        Toast.makeText(getApplicationContext(), "Employee Updated Successfully", Toast.LENGTH_SHORT).show();
                        BackHome();
                    }
                }
            }
        });
    }

    private void BackHome()
    {
        Intent i = new Intent(this,DatabaseActivity.class);
        startActivity(i);
    }
    private void ControlInitialization() {
        txtEmpName =findViewById(R.id.txtEmpName);
        txtContactNo = findViewById(R.id.txtContactNo);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
    }
}