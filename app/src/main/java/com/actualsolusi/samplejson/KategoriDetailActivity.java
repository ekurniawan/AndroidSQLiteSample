package com.actualsolusi.samplejson;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import models.Kategori;
import services.KategoriServices;

public class KategoriDetailActivity extends AppCompatActivity {
    private EditText txtKategoriID,txtNamaKategori;
    private Button btnDelete,btnEdit;
    private Kategori kategori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_detail);
        final int id = getIntent().getIntExtra("id",0);
        showDetail(id);

        txtKategoriID = (EditText) findViewById(R.id.txtKategoriID);
        txtNamaKategori = (EditText) findViewById(R.id.txtNamaKategori);

        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnEdit = (Button)findViewById(R.id.btnEdit);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KategoriDetailActivity.this);
                builder.setMessage("Apakah anda yakin untuk delete data Kategori?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteKategori(id);
                            }
                        }).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKategori();
            }
        });
    }

    private void updateKategori(){
        new AsyncTask<Void,Void,Void>(){
            int status = 0;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                kategori.setKategoriID(Integer.parseInt(txtKategoriID.getText().toString()));
                kategori.setNamaKategori(txtNamaKategori.getText().toString());
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    status = KategoriServices.updateKategori(kategori);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(status==200){
                    Toast.makeText(KategoriDetailActivity.this,"Update data berhasil !",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(KategoriDetailActivity.this,"Gagal untuk delete data "+String.valueOf(status),Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    private void deleteKategori(final int id){
        new AsyncTask<Void,Void,Void>(){
            private int status;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    status = KategoriServices.deleteKategori(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(status==200){
                    Toast.makeText(KategoriDetailActivity.this,"Data berhasil di delete !",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(KategoriDetailActivity.this,"Gagal untuk delete data "+String.valueOf(status),Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    private void showDetail(final int id){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    kategori = KategoriServices.GetKategoriById(id);
                } catch (IOException e) {
                    Log.d("MyServices",e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                txtKategoriID.setText(String.valueOf(kategori.getKategoriID()));
                txtNamaKategori.setText(kategori.getNamaKategori());
            }
        }.execute();
    }
}
