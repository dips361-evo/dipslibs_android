package com.evo.mitzoom.Helper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evo.mitzoom.API.Server;
import com.evo.mitzoom.Session.SessionManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionRabbitHttp {

    private static String TAG = "ConnectionRabbitHttp";
    private static SessionManager sessions;
    private static String idDips;

    public static void init(Context mContext) {
        sessions = new SessionManager(mContext);
        idDips = sessions.getKEY_IdDips();
    }

    public interface getTicketInfoCallbacks {
        void onSuccess(@NonNull String value);

        void onError(@NonNull Throwable throwable);
    }

    public static void getTicket(@Nullable getTicketInfoCallbacks callbacks) {
        JSONObject dataObj = new JSONObject();
        try {
            dataObj.put("custId", idDips);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG,"getTicket dataObj : "+ dataObj.toString());

        String authAccess = "Bearer "+sessions.getAuthToken();
        String exchangeToken = sessions.getExchangeToken();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), dataObj.toString());

        Server.getAPIServiceRabbitHttp().RabbHttpGetTicketCurrent(requestBody,authAccess,exchangeToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e(TAG,"getTicket code : "+response.code());
                if (response.isSuccessful()) {
                    String dataS = response.body().toString();
                    Log.e(TAG,"getTicket body : "+response.body());
                    try {
                        JSONObject bodyObj = new JSONObject(dataS);
                        String ticketLast = bodyObj.getString("ticket");
                        int ticketLastInt = Integer.parseInt(ticketLast);
                        String lastQueue = String.format("%03d", ticketLastInt);
                        Log.e(TAG, "getMyTicket lastQueue : " + lastQueue);
                        callbacks.onSuccess(lastQueue);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callbacks.onError(t);
            }
        });
    }

    public static void getMyTicket(@Nullable getTicketInfoCallbacks callbacks) {
        JSONObject dataObj = new JSONObject();
        try {
            dataObj.put("custId", idDips);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG,"getMyTicket dataObj : "+ dataObj.toString());

        String authAccess = "Bearer "+sessions.getAuthToken();
        String exchangeToken = sessions.getExchangeToken();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), dataObj.toString());

        Server.getAPIServiceRabbitHttp().RabbHttpGetMyTicket(requestBody,authAccess,exchangeToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e(TAG,"getMyTicket code : "+response.code());
                if (response.isSuccessful()) {
                    String dataS = response.body().toString();
                    Log.e(TAG,"getMyTicket body : "+response.body());
                    try {
                        JSONObject bodyObj = new JSONObject(dataS);
                        String getTicket = bodyObj.getString("ticket");
                        int myTicketInt = Integer.parseInt(getTicket);
                        String myTicketNumber = String.format("%03d", myTicketInt);
                        Log.e(TAG, "getMyTicket lastQueue : " + myTicketNumber);
                        callbacks.onSuccess(myTicketNumber);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callbacks.onError(t);
            }
        });
    }

    public static void
    listenCall(@Nullable getTicketInfoCallbacks callbacks) {
        JSONObject dataObj = new JSONObject();
        try {
            dataObj.put("custId", idDips);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG,"listenCall dataObj : "+ dataObj.toString());

        String authAccess = "Bearer "+sessions.getAuthToken();
        String exchangeToken = sessions.getExchangeToken();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), dataObj.toString());

        Server.getAPIServiceRabbitHttp().RabbHttpListenCall(requestBody,authAccess,exchangeToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e(TAG,"listenCall code : "+response.code());
                if (response.isSuccessful()) {
                    String dataS = response.body().toString();
                    Log.e(TAG,"listenCall body : "+response.body());
                    callbacks.onSuccess(dataS);
                    try {
                        JSONObject bodyObj = new JSONObject(dataS);
                        String actionCall = bodyObj.getString("action");
                        if (actionCall.equals("info")) {
                            Log.e(TAG,"MASUK INFO HIT LAGI");
                            listenCall(callbacks);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else if (response.code() == 408) {
                    listenCall(callbacks);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callbacks.onError(t);
                listenCall(callbacks);
            }
        });
    }

    public static void acceptCall(JSONObject dataObj) {
        Log.e(TAG,"acceptCall dataObj : "+ dataObj.toString());

        String authAccess = "Bearer "+sessions.getAuthToken();
        String exchangeToken = sessions.getExchangeToken();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), dataObj.toString());

        Server.getAPIServiceRabbitHttp().RabbHttpAcceptCall(requestBody,authAccess,exchangeToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e(TAG,"acceptCall code : "+response.code());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public static void mirroringEndpoint(int endpoint) {
        String csID = sessions.getCSID();
        JSONObject dataObj = new JSONObject();
        try {
            dataObj.put("csId", csID);
            dataObj.put("endpoint", endpoint);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG,"MirroringEndpoint dataObj : "+ dataObj.toString());

        String authAccess = "Bearer "+sessions.getAuthToken();
        String exchangeToken = sessions.getExchangeToken();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), dataObj.toString());

        Server.getAPIServiceRabbitHttp().RabbHttpMirroringEndpoint(requestBody,authAccess,exchangeToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e(TAG,"MirroringEndpoint code : "+response.code());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public static void mirroringKey(JSONObject dataObj) {
        JSONObject datax = dataMirroring(dataObj);
        String dataxS = datax.toString();
        Log.e(TAG,"MirroringKey dataObj : "+ dataxS.toString());

        String authAccess = "Bearer "+sessions.getAuthToken();
        String exchangeToken = sessions.getExchangeToken();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), dataxS.toString());

        Server.getAPIServiceRabbitHttp().RabbHttpMirroringKey(requestBody,authAccess,exchangeToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e(TAG,"MirroringKey code : "+response.code());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private static JSONObject dataMirroring(JSONObject dataObj) {
        String csID = sessions.getCSID();

        JSONObject jsObj = new JSONObject();
        try {
            jsObj.put("csId",csID);
            jsObj.put("transaction",dataObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsObj;
    }
}
