package com.actualsolusi.samplejson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class DetailBarangActivity extends AppCompatActivity {
    private ImageView imgDetailGambar;
    private EditText txtDetailBarangID, txtDetailNamaBarang,txtDetailDeskripsi,txtDetailStok,txtDetailHargaBeli,txtDetailHargaJual;
    private Button btnDetailUpdate,btnDetailDelete;
    private Spinner spinnerDetailKategori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        spinnerDetailKategori = (Spinner)findViewById(R.id.spinnerDetailKategori);

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
