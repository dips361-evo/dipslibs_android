package com.evo.mitzoom.Helper;

import android.util.Log;

import com.evo.mitzoom.API.Server;
import com.evo.mitzoom.BaseMeetingActivity;
import com.evo.mitzoom.ui.Alternative.DipsSwafoto;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GlobalExceptionHandler {


    public static void getLog(Exception e){
        try{
            Log.e("TAG","LOG = "+Log.getStackTraceString(e));
            JSONObject objReq = new JSONObject();
            String logInfo = Log.getStackTraceString(e);
            objReq.put("log",logInfo);
            sendLogger(objReq);
        }
        catch (Exception exception){
            Log.e("TAG",exception.getMessage());
        }

    }

    public static void sendLogger(JSONObject dataReq) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), dataReq.toString());

        Server.getAPIServiceLogger().LoggerAndroid(requestBody).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("TAG","Response = "+response);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
