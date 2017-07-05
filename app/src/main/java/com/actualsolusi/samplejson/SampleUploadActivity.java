package com.actualsolusi.samplejson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import services.FileUploadServices;

public class SampleUploadActivity extends AppCompatActivity {
    private Button btnCapture,btnUpload;
    private ImageView imgPic;
    private Bitmap imageBitmap;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCapture = (Button)findViewById(R.id.btnCapture);
        btnUpload = (Button)findViewById(R.id.btnUpload);
        imgPic = (ImageView)findViewById(R.id.imgPic);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCapture = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCapture,0);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap)extras.get("data");
            imgPic.setImageBitmap(imageBitmap);
        }
    }

    private void uploadImage(){
        new AsyncTask<Bitmap,Void,Void>(){

            @Override
            protected Void doInBackground(Bitmap... params) {
                if(params[0]==null)
                    return null;

                File f = new File(getApplicationContext().getCacheDir(),"sample.png");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    Log.d("FileUpload",e.getLocalizedMessage());
                }

                Bitmap bitmap = params[0];
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
                byte[] bitmapData = stream.toByteArray();

                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(f);
                    fos.write(bitmapData);
                    fos.flush();
                    fos.close();

                    status = FileUploadServices.UploadPic(f,"sample.png");
                } catch (IOException e) {
                    Log.d("FileUpload",e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(status==200)
                    Toast.makeText(SampleUploadActivity.this,"Berhasil upload picture !",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(SampleUploadActivity.this,"Data gagal diupload !",Toast.LENGTH_LONG).show();
            }
        }.execute(imageBitmap);

    }
}
