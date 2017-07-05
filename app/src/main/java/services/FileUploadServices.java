package services;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by erick on 30/06/2017.
 */

public final class FileUploadServices {
    private static final String SERVICES_URL = "http://10.0.2.2:8088/";
    private static final OkHttpClient client;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS).build();
    }

    public static int UploadPic(File image,String imageName) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",imageName,RequestBody.create(MEDIA_TYPE_PNG,image))
                .build();
        Request request = new Request.Builder().url(SERVICES_URL+"api/Upload/PostUserImage")
                .post(requestBody).build();

        Response response = client.newCall(request).execute();

        if(!response.isSuccessful()){
            throw new IOException("Unexpected code " + String.valueOf(response.code()));
        }else{
            return response.code();
        }
    }
}
