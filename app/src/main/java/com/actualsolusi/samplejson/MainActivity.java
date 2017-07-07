package com.actualsolusi.samplejson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import models.Kategori;
import models.TokenOutput;
import services.KategoriServices;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private ListView lvKategori;
    private List<Kategori> listKategori;
    private Button btnCheck;
    private TokenOutput myToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnCheck = (Button) findViewById(R.id.btn_check);


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });

        lvKategori = (ListView)findViewById(R.id.lvKategori);

        lvKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this,KategoriDetailActivity.class);
                Kategori kategori = listKategori.get(position);
                detailIntent.putExtra("id",kategori.getKategoriID());
                startActivity(detailIntent);
            }
        });

    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);

        boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected)
            loadContent();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void loadContent(){
        new AsyncTask<Void,Void,Void>(){
            ProgressDialog progress;

            /*@Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(MainActivity.this);
                progress.setMessage("loading...");
                progress.show();
            }*/

            @Override
            protected Void doInBackground(Void... params) {
                try{
                    //listKategori = KategoriServices.GetAllKategori();
                    myToken = KategoriServices.postToken();
                    Log.v("MyServices",myToken.getAccessToken());
                    //Log.v("MyServices",listKategori.get(0).getNamaKategori());
                } catch (IOException e) {
                    Log.d("MyServices",e.getLocalizedMessage());
                }
                return null;
            }

            /*@Override
            protected void onPostExecute(Void aVoid) {
                ArrayAdapter<Kategori> adapterKategori = new ArrayAdapter<Kategori>(getApplicationContext(),
                        android.R.layout.simple_list_item_2, text1,listKategori){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if(convertView==null){
                            convertView = LayoutInflater.from(getContext())
                                    .inflate(android.R.layout.simple_list_item_2,parent,false);
                        }
                        TextView text1 = (TextView)convertView.findViewById(android.R.id.text1);
                        TextView text2 = (TextView)convertView.findViewById(android.R.id.text2);

                        Kategori kategori = listKategori.get(position);
                        text1.setText(String.valueOf(kategori.getKategoriID()));
                        text2.setText(kategori.getNamaKategori());
                        return convertView;
                    }
                };
                lvKategori.setAdapter(adapterKategori);
                if(progress!=null){
                    progress.dismiss();
                }
            }*/
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tambahKategori) {
            Intent tambahIntent = new Intent(MainActivity.this,TambahKategoriActivity.class);
            startActivity(tambahIntent);
        }
        else if(id == R.id.action_upload){
            Intent i = new Intent(MainActivity.this,SampleUploadActivity.class);
            startActivity(i);
        }
        else if(id==R.id.action_tambahBarang){
            Intent i = new Intent(MainActivity.this,TambahBarangActivity.class);
            startActivity(i);
        }
        else if(id==R.id.action_listBarang){
            Intent i = new Intent(MainActivity.this,ListBarangActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
