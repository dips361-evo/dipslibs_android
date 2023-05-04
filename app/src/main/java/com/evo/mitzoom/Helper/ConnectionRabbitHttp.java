package com.evo.mitzoom.Helper;

import android.content.Context;
import android.util.Log;

import com.evo.mitzoom.API.Server;
import com.evo.mitzoom.Session.SessionManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class ConnectionRabbitHttp {

    //RabitMQ
    static ConnectionFactory connectionFactory = new ConnectionFactory();
    private static Connection connection = null;
    private static Thread publishThread;
    private static Thread publishEndpointThread;
    private static Context mContext;
    private static SessionManager sessions;
    private static final String TAG = "ConnectionRabbitHttp";
    private static Channel chSendKey = null;
    private static Channel chSendEndpoint = null;
    private static int operations; // 1 = RabbitMQ, 2 = Https

    public ConnectionRabbitHttp(Context mContext, int Operations) {
        ConnectionRabbitHttp.mContext = mContext;
        ConnectionRabbitHttp.operations = Operations;

        if (Operations == 1) {
            setupConnectionFactory();
        }
    }

    private void setupConnectionFactory() {
        sessions = new SessionManager(mContext);

        String uriRabbit = Server.BASE_URL_RABBITMQ;
        Log.e(TAG,"MASUK setupConnectionFactory uriRabbit : "+uriRabbit);
        try {
            connectionFactory.setAutomaticRecoveryEnabled(false);
            connectionFactory.setUri(uriRabbit);
            connection = connectionFactory.newConnection();
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            Log.e(TAG,"ERROR setupConnectionFactory : "+e.getMessage());
            e.printStackTrace();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public static void RabbitGetTicketInfo() {
        if (operations == 1) {

        }
    }
}
