package com.actualsolusi.samplejson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import models.Barang;

/**
 * Created by erick on 30/06/2017.
 */

public class BarangListAdapter extends ArrayAdapter<Barang> {
    private List<Barang> listBarang;

    public BarangListAdapter(Context context, int resource, List<Barang> objects) {
        super(context, resource, objects);
        listBarang = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.barang_item,parent,false);
        }
        Barang barang = listBarang.get(position);

        TextView tvNamaBarang = (TextView)convertView.findViewById(R.id.tvNamaBarang);
        tvNamaBarang.setText(barang.getNamaBarang());

        TextView tvStok = (TextView)convertView.findViewById(R.id.tvStok);
        tvStok.setText(String.valueOf(barang.getStok()));

        TextView tvHargaJual = (TextView)convertView.findViewById(R.id.tvHargaJual);
        tvHargaJual.setText(String.valueOf(barang.getHargaJual()));

        ImageView imgPic = (ImageView)convertView.findViewById(R.id.imgPic);
        try {
            //Log.v("BarangListAdapter",getContext().getFilesDir().getAbsolutePath());
            Log.v("BarangListAdapter",barang.getGambar());
            Bitmap bitmap = loadImageFromStorage("/data/user/0/com.actualsolusi.samplejson/app_imageDir",
                    barang.getGambar().toString().trim());
            imgPic.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private Bitmap loadImageFromStorage(String path, String filename) throws FileNotFoundException {
        File f = new File(path,filename);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }
}
