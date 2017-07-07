package com.actualsolusi.samplejson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import models.Kategori;

/**
 * Created by erick on 01/07/2017.
 */

public class SpinnerKategoriAdapter extends ArrayAdapter<Kategori> {

    private Context context;
    private List<Kategori> listKategori = new ArrayList<>();

    public SpinnerKategoriAdapter(Context context, int resource, List<Kategori> listKategori) {
        super(context, resource, listKategori);
        this.listKategori = listKategori;
    }


    @Override
    public int getCount() {
        return listKategori.size();
    }

    @Override
    public Kategori getItem(int position) {
        return listKategori.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_spinner_item,parent,false);

        }
        TextView text1 = (TextView)convertView.findViewById(android.R.id.text1);
        text1.setText(listKategori.get(position).getNamaKategori());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_spinner_item,parent,false);

        }
        TextView text1 = (TextView)convertView.findViewById(android.R.id.text1);
        text1.setText(listKategori.get(position).getNamaKategori());
        return convertView;
    }
}
