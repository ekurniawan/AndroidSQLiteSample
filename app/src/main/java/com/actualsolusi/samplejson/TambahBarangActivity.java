package com.actualsolusi.samplejson;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import localdata.DatabaseHelper;
import models.Barang;
import models.Kategori;

public class TambahBarangActivity extends AppCompatActivity {
    private ImageView imgGambar;
    private Bitmap imageBitmap;
    private EditText txtBarangID,txtNamaBarang,txtDeskripsi,txtStok,txtHargaBeli,txtHargaJual;
    private Button btnTambah;
    private DatabaseHelper dbHelper;
    private Spinner spinnerKategori;
    private Kategori kategoriSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        dbHelper = new DatabaseHelper(TambahBarangActivity.this);

        imgGambar = (ImageView)findViewById(R.id.imgGambar);
        txtBarangID = (EditText)findViewById(R.id.txtBarangID);
        txtNamaBarang = (EditText)findViewById(R.id.txtNamaBarang);
        txtDeskripsi = (EditText)findViewById(R.id.txtDeskripsi);
        txtStok = (EditText)findViewById(R.id.txtStok);
        txtHargaBeli = (EditText)findViewById(R.id.txtHargaBeli);
        txtHargaJual = (EditText)findViewById(R.id.txtHargaJual);
        btnTambah = (Button)findViewById(R.id.btnTambah);
        spinnerKategori = (Spinner)findViewById(R.id.spinnerKategori);

        imgGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCapture = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCapture,0);
            }
        });
        //dbHelper.DeleteAllKategori();
        //dbHelper.SeedingKategori();

        List<Kategori> listKategori = dbHelper.GetAllKategori();
        //dbHelper.DeleteAllBarang();
        //String nama = String.valueOf(listKategori.get(0).getNamaKategori());
        //String strsize = String.valueOf(listKategori.size());
        //Toast.makeText(TambahBarangActivity.this,nama, Toast.LENGTH_LONG).show();

        SpinnerKategoriAdapter spinnerAdapter = new SpinnerKategoriAdapter(TambahBarangActivity.this,
                android.R.layout.simple_spinner_item,listKategori);
        spinnerKategori.setAdapter(spinnerAdapter);

        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategoriSelected = (Kategori)parent.getItemAtPosition(position);
                //Toast.makeText(TambahBarangActivity.this,String.valueOf(kategoriSelected.getKategoriID()),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Barang barang = new Barang();
                barang.setBarangID(txtBarangID.getText().toString().trim());
                barang.setKategoriID(kategoriSelected.getKategoriID());
                barang.setNamaBarang(txtNamaBarang.getText().toString().trim());
                barang.setDeskripsi(txtDeskripsi.getText().toString().trim());
                barang.setStok(Integer.valueOf(txtStok.getText().toString().trim()));
                barang.setHargaBeli(Double.valueOf(txtHargaBeli.getText().toString().trim()));
                barang.setHargaJual(Double.valueOf(txtHargaJual.getText().toString().trim()));
                barang.setGambar(txtBarangID.getText().toString().trim()+".png");
                barang.setSync(0);

                long status = dbHelper.InsertBarang(barang);
                if(status!=-1){
                    try {
                        String path = saveToInternalStorage(imageBitmap);
                        Toast.makeText(TambahBarangActivity.this,"Berhasil menyimpan data pada "+path,Toast.LENGTH_LONG).show();
                        Log.v("TambahBarangActivity",path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String saveToInternalStorage(Bitmap bitmapImage) throws IOException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir",MODE_PRIVATE);
        File mypath = new File(directory,txtBarangID.getText().toString().trim()+".png");
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG,100,fos);
            Log.v("TambahBarangActivity",directory.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fos.close();
        }

        return directory.getAbsolutePath();
    }

    private Bitmap loadImageFromStorage(String path,String filename) throws FileNotFoundException {
        File f = new File(path,filename);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap)extras.get("data");
            imgGambar.setImageBitmap(imageBitmap);
        }
    }
}
