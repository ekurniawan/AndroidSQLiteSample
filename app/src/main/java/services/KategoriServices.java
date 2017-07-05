package services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import models.Kategori;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by erick on 29/06/2017.
 */

public final class KategoriServices {
    private static final String SERVICES_URL = "http://actservices.azurewebsites.net/";
    //private static final String SERVICES_URL = "http://10.0.2.2:8088/";
    private static final OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS).build();
    }

    public static Kategori GetKategoriById(int id) throws IOException {
        Kategori kategori = new Kategori();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVICES_URL + "api/Kategori").newBuilder();
        urlBuilder.addPathSegment(String.valueOf(id));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        try {
            JSONObject jsonObject = new JSONObject(result);
            if(!jsonObject.isNull("KategoriID")){
                kategori.setKategoriID(jsonObject.getInt("KategoriID"));
                kategori.setNamaKategori(jsonObject.getString("NamaKategori"));
            }

        } catch (JSONException e) {
            Log.d("MyServices",e.getLocalizedMessage());
        }
        return kategori;
    }

    public static List<Kategori> GetAllKategori() throws IOException {
        List<Kategori> listKategori = new ArrayList<>();
        Request request = new Request.Builder()
                .url(SERVICES_URL+"api/Kategori").build();
        Response response = client.newCall(request).execute();

        String results = response.body().string();

        try{
            JSONArray jsonArray = new JSONArray(results);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Kategori kategori = new Kategori();
                kategori.setKategoriID(jsonObject.getInt("KategoriID"));
                kategori.setNamaKategori(jsonObject.getString("NamaKategori"));
                listKategori.add(kategori);
            }

        }catch(JSONException jEx){
            Log.d("MyServices",jEx.getLocalizedMessage());
        }
        return listKategori;
    }

    public static int postKategori(Kategori kategori) throws JSONException, IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVICES_URL + "api/Kategori").newBuilder();
        String url = urlBuilder.build().toString();

        JSONObject newKategori = new JSONObject();
        newKategori.put("NamaKategori",kategori.getNamaKategori());

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                newKategori.toString());
        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = client.newCall(request).execute();

        return response.code();
        //Log.d("PostKategori",String.valueOf(response.code()));
    }

    public static int updateKategori(Kategori kategori) throws JSONException, IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVICES_URL + "api/Kategori").newBuilder();
        String url = urlBuilder.build().toString();

        JSONObject editKategori = new JSONObject();
        editKategori.put("NamaKategori",kategori.getNamaKategori());
        editKategori.put("KategoriID",kategori.getKategoriID());

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                editKategori.toString());
        Request request = new Request.Builder()
                .url(url).put(body).build();

        Response response = client.newCall(request).execute();

        return response.code();
    }

    public static int deleteKategori(int id) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVICES_URL + "api/Kategori").newBuilder();
        urlBuilder.addPathSegment(String.valueOf(id));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).delete().build();
        Response response = client.newCall(request).execute();

        return response.code();
    }
}
