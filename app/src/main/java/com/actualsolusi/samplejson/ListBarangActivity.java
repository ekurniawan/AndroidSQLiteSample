package com.actualsolusi.samplejson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import localdata.DatabaseHelper;
import models.Barang;

public class ListBarangActivity extends AppCompatActivity {
    private ListView listViewBarang;
    private List<Barang> listBarang;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);
        dbHelper = new DatabaseHelper(ListBarangActivity.this);

        listBarang = dbHelper.GetAllBarang();
        listViewBarang = (ListView)findViewById(R.id.listViewBarang);

        BarangListAdapter adapter = new BarangListAdapter(this,R.layout.barang_item,listBarang);
        listViewBarang.setAdapter(adapter);

        listViewBarang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListBarangActivity.this,DetailBarangActivity.class);
                i.putExtra("BarangID",listBarang.get(position).getBarangID());
                startActivity(i);
            }
        });
    }
}
