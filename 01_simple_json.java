package com.me.json;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String data = "";

    ArrayList<String> id, name, country, city, img, opdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lvList);
        fetchJsonData();


    }

    private void fetchJsonData() {
        String URL = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";
//        id =new  ArrayList<String>();
//        name = new ArrayList<String>();
//        country = new ArrayList<String>();
//        city = new ArrayList<String>();
//        img = new ArrayList<String>();
        opdata = new ArrayList<String>();

        StringRequest req = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        data = object.getString("_id") + " , \n " + object.getString("Product_Name") + " ,\n " + object.getString("Price");

                        opdata.add(data);

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, opdata);
                    lv.setAdapter(adapter);
                    
                } catch (Exception fenil) {
                    fenil.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(req);
//        RequestQueue rq = Volley.newRequestQueue(this);
//        rq.add(req);
    }
}
