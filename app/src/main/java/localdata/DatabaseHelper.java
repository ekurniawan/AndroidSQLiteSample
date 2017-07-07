package localdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import models.Barang;
import models.Kategori;

/**
 * Created by erick on 30/06/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //logcat
    private static final String LOG = DatabaseHelper.class.getName();

    //versi database
    private static final int DATABASE_VERSION = 1;

    //database name
    private static  final String DATABASE_NAME = "BackendLocalDb";

    //table name
    private static final String Table_Kategori = "Kategori";
    private static final String Table_Barang = "Barang";

    //table Kategori
    private static final String KategoriID = "KategoriID";
    private static final String NamaKategori = "NamaKategori";

    //table Barang
    private static final String BarangID = "BarangID";
    private static final String NamaBarang = "NamaBarang";
    private static final String Deskripsi = "Deskripsi";
    private static final String Stok = "Stok";
    private static final String HargaBeli = "HargaBeli";
    private static final String HargaJual = "HargaJual";
    private static final String Gambar = "Gambar";
    private static final String isSync = "isSync";

    //create table
    private static final String Create_Table_Kategori = "create table "+Table_Kategori+
            "("+KategoriID+" integer primary key autoincrement,"+NamaKategori+" text);";
    private static final String Create_Table_Barang = "create table "+Table_Barang+
            "("+BarangID+" text primary key,"+KategoriID+" integer,"+NamaBarang+" text,"
            +Deskripsi+" text,"+Stok+" integer,"+HargaBeli+" real,"
            +HargaJual+" real,"+Gambar+" text, "+isSync+" integer);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(LOG,Create_Table_Kategori);
        Log.e(LOG,Create_Table_Barang);

        db.execSQL(Create_Table_Kategori);
        db.execSQL(Create_Table_Barang);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Table_Kategori);
        db.execSQL("drop table if exists "+Table_Barang);
        onCreate(db);
    }

    //region Table Barang Method

    public long InsertBarang(Barang barang){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BarangID,barang.getBarangID());
        values.put(KategoriID,barang.getKategoriID());
        values.put(NamaBarang,barang.getNamaBarang());
        values.put(Deskripsi,barang.getDeskripsi());
        values.put(Stok,barang.getStok());
        values.put(HargaBeli,barang.getHargaBeli());
        values.put(HargaJual,barang.getHargaJual());
        values.put(Gambar,barang.getGambar());
        values.put(isSync,barang.isSync());

        long status = db.insert(Table_Barang,null,values);
        db.close();
        return status;
    }

    public int UpdateBarang(Barang barang){
        int status;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BarangID,barang.getBarangID());
        values.put(KategoriID,barang.getKategoriID());
        values.put(NamaBarang,barang.getNamaBarang());
        values.put(Deskripsi,barang.getDeskripsi());
        values.put(Stok,barang.getStok());
        values.put(HargaBeli,barang.getHargaBeli());
        values.put(HargaJual,barang.getHargaJual());
        values.put(Gambar,barang.getGambar());
        values.put(isSync,barang.isSync());

        status = db.update(Table_Barang,values,BarangID+"=?",
                new String[]{barang.getBarangID()});
        db.close();

        return status;
    }

    public int DeleteBarang(String barangID){
        int status;
        SQLiteDatabase db = this.getWritableDatabase();
        status = db.delete(Table_Barang,BarangID+"=?",new String[]{barangID});
        return status;
    }

    public void DeleteAllBarang(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Table_Barang);
    }

    public List<Barang> GetAllBarang(){
        List<Barang> listBarang = new ArrayList<>();
        String strSql = "select * from "+Table_Barang+" order by "+NamaBarang+" asc";
        Log.e(LOG,strSql);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(strSql,null);
        if(c.moveToFirst()){
            do{
                Barang barang = new Barang();
                barang.setBarangID(c.getString(c.getColumnIndex(BarangID)));
                barang.setKategoriID(c.getInt(c.getColumnIndex(KategoriID)));
                barang.setNamaBarang(c.getString(c.getColumnIndex(NamaBarang)));
                barang.setDeskripsi(c.getString(c.getColumnIndex(Deskripsi)));
                barang.setStok(c.getInt(c.getColumnIndex(Stok)));
                barang.setHargaBeli(c.getDouble(c.getColumnIndex(HargaBeli)));
                barang.setHargaJual(c.getDouble(c.getColumnIndex(HargaJual)));
                barang.setGambar(c.getString(c.getColumnIndex(Gambar)));
                barang.setSync(c.getInt(c.getColumnIndex(isSync)));

                listBarang.add(barang);
            }while (c.moveToNext());
        }
        db.close();

        return listBarang;
    }



    public Barang GetBarangById(String barangID){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSql = "select * from "+Table_Barang+" where "+BarangID+"=?";
        Log.e(LOG,strSql);

        Cursor c = db.rawQuery(strSql,new String[]{barangID});
        if(c!=null)
            c.moveToFirst();
        Barang barang = new Barang();
        barang.setBarangID(c.getString(c.getColumnIndex(BarangID)));
        barang.setKategoriID(c.getInt(c.getColumnIndex(KategoriID)));
        barang.setNamaBarang(c.getString(c.getColumnIndex(NamaBarang)));
        barang.setDeskripsi(c.getString(c.getColumnIndex(Deskripsi)));
        barang.setStok(c.getInt(c.getColumnIndex(Stok)));
        barang.setHargaBeli(c.getDouble(c.getColumnIndex(HargaBeli)));
        barang.setHargaJual(c.getDouble(c.getColumnIndex(HargaJual)));
        barang.setGambar(c.getString(c.getColumnIndex(Gambar)));
        barang.setSync(c.getInt(c.getColumnIndex(isSync)));

        db.close();

        return barang;
    }

    //endregion

    //region Table Kategori

    public List<Kategori> GetAllKategori(){
        List<Kategori> listKategori = new ArrayList<>();
        String strSql = "select * from "+Table_Kategori+" order by "+NamaKategori+" asc";
        Log.e(LOG,strSql);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(strSql,null);
        if(c.moveToFirst()){
            do{
                Kategori kategori = new Kategori();
                kategori.setKategoriID(c.getInt(c.getColumnIndex(KategoriID)));
                kategori.setNamaKategori(c.getString(c.getColumnIndex(NamaKategori)));

                listKategori.add(kategori);
            }while (c.moveToNext());
        }
        db.close();

        return listKategori;
    }

    /*public int GetKategoriByIdPos(int kategoriID){
        String strSql = "select * from "+Table_Kategori+" where KategoriID=? order by "+NamaKategori+" asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(strSql,new String[]{String.valueOf(kategoriID)});
        if(c!=null)
            c.moveToFirst();
    }*/

    public long InsertKategori(Kategori kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NamaKategori,kategori.getNamaKategori());

        long status = db.insert(Table_Kategori,null,values);

        db.close();
        return status;
    }

    public void DeleteAllKategori(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+Table_Kategori);

    }

    public void SeedingKategori(){
            Kategori kat1 = new Kategori();
            kat1.setNamaKategori("Shirt");
            InsertKategori(kat1);

            Kategori kat2 = new Kategori();
            kat2.setNamaKategori("Jacket");
            InsertKategori(kat2);

            Kategori kat3 = new Kategori();
            kat3.setNamaKategori("Pants");
            InsertKategori(kat3);

            Kategori kat4 = new Kategori();
            kat4.setNamaKategori("Vest");
            InsertKategori(kat4);

            Kategori kat5 = new Kategori();
            kat5.setNamaKategori("Blouse");
            InsertKategori(kat5);
    }

    //endregion
}
