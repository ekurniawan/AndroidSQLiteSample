package com.actualsolusi.samplejson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

import localdata.DatabaseHelper;
import models.Barang;
import models.Kategori;

public class DetailBarangActivity extends AppCompatActivity {
    private ImageView imgDetailGambar;
    private EditText txtDetailBarangID, txtDetailNamaBarang,txtDetailDeskripsi,txtDetailStok,txtDetailHargaBeli,txtDetailHargaJual;
    private Button btnDetailUpdate,btnDetailDelete;
    private Spinner spinnerDetailKategori;
    private List<Barang> listBarang;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        dbHelper = new DatabaseHelper(DetailBarangActivity.this);

        String barangId = getIntent().getStringExtra("BarangID");
    Log.d("MyLog",barangId);

        Barang barang = dbHelper.GetBarangById(barangId);

        List<Kategori> listKategori = dbHelper.GetAllKategori();
        spinnerDetailKategori = (Spinner)findViewById(R.id.spinnerDetailKategori);
        SpinnerKategoriAdapter spinnerAdapter = new SpinnerKategoriAdapter(DetailBarangActivity.this,
                android.R.layout.simple_spinner_item,listKategori);
        spinnerDetailKategori.setAdapter(spinnerAdapter);

        for(int i=0;i<spinnerAdapter.getCount();i++)
        {
            if(listKategori.get(i).getKategoriID()==barang.getKategoriID())
                spinnerDetailKategori.setSelection(i);
        }

    }

    /*String compareValue = "some value";
ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_state, android.R.layout.simple_spinner_item);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
mSpinner.setAdapter(adapter);
if (!compareValue.equals(null)) {
    int spinnerPosition = adapter.getPosition(compareValue);
    mSpinner.setSelection(spinnerPosition);
}*/


}
