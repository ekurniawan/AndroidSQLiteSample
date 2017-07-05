package com.actualsolusi.samplejson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import models.Kategori;
import services.KategoriServices;

public class TambahKategoriActivity extends AppCompatActivity {
    private Button btnTambah;
    private EditText txtNamaKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kategori);

        txtNamaKategori = (EditText)findViewById(R.id.txtNamaKategori);

        btnTambah = (Button)findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahKategori();
            }
        });
    }

    private void tambahKategori(){
        new AsyncTask<Void,Void,Void>(){
            Kategori newKategori = new Kategori();
            int status =0;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    status = KategoriServices.postKategori(newKategori);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(status==200){
                    Toast.makeText(getApplicationContext(),"Data Kategori berhasil ditambah !",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Gagal menambah data !",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected void onPreExecute() {
                newKategori.setNamaKategori(txtNamaKategori.getText().toString());
            }
        }.execute();
    }
}
