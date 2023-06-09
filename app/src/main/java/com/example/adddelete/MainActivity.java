package com.example.adddelete;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button ap_add;
    EditText ap_name, ap_price ,ap_loc , ap_rooms;
    ListView lv_ap;
    ArrayAdapter apartArrayAdapter;
    Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // on create, give value
        ap_add = findViewById(R.id.ap_add);
        ap_name=findViewById(R.id.ap_name);
        ap_price = findViewById(R.id.ap_price);
        ap_loc=findViewById(R.id.ap_loc);
        ap_rooms = findViewById(R.id.ap_rooms);
        lv_ap = findViewById(R.id.lv_ap);

        database = new Database(MainActivity.this);
        ShowApartOnListView(database);

        ap_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create model
                apart apartMod;
                try {
                    apartMod = new apart(-1, ap_name.getText().toString(), Integer.parseInt(ap_price.getText().toString()), ap_loc.getText().toString(), Integer.parseInt(ap_rooms.getText().toString()));
                    Toast.makeText(MainActivity.this, apartMod.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Enter Valid input", Toast.LENGTH_SHORT).show();
                    apartMod = new apart(-1, "ERROR", 0 ,"ERROR",0);
                }

                Database database = new Database(MainActivity.this);
                boolean b = database.addOne(apartMod);
                Toast.makeText(MainActivity.this, "SUCCESS= "+ b, Toast.LENGTH_SHORT).show();

                ShowApartOnListView(database);

                database = new Database(MainActivity.this);
                ShowApartOnListView(database);

            }
        });

        lv_ap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                apart ClickedApart = (apart) adapterView.getItemAtPosition(i);
                database.DeleteOne(ClickedApart);
                ShowApartOnListView(database);
                Toast.makeText(MainActivity.this, "Deleted" + ClickedApart.toString(), Toast.LENGTH_SHORT).show();
            }
        });






    }

    private void ShowApartOnListView(Database database) {
        apartArrayAdapter = new ArrayAdapter<apart>(MainActivity.this, android.R.layout.simple_list_item_1, database.getEveryone());
        lv_ap.setAdapter(apartArrayAdapter);
    }
}