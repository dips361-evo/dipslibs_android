package com.evo.mitzoom.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.evo.mitzoom.R;
import com.evo.mitzoom.Session.SessionManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class DownloadTaskHelper extends AsyncTask<String, Integer, String> {

    private final Context context;
    private final SessionManager sessionManager;
    private PowerManager.WakeLock mWakeLock;
    private final ProgressDialog mProgressDialog;

    public DownloadTaskHelper(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.mProgressDialog = progressDialog;
        sessionManager = new SessionManager(this.context);
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;

        try {
            SSLContext ssl_ctx = SSLContext.getInstance("TLS");
            TrustManager[ ] trust_mgr = new TrustManager[ ] {
                    new X509TrustManager() {
                        public X509Certificate[ ] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[ ] certs, String t) { }
                        public void checkServerTrusted(X509Certificate[ ] certs, String t) { }
                    }
            };
            ssl_ctx.init(null,                // key manager
                    trust_mgr,           // trust manager
                    new SecureRandom()); // random number generator
            HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        HttpsURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization","Bearer "+sessionManager.getAuthToken());
            connection.setRequestProperty("exchangeToken",sessionManager.getExchangeToken());
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            File dir = createDir();
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                }
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            String filename = "FORM_CIF_"+timeStamp+".pdf";
            if (sUrl[1] != null) {
                if (!sUrl[1].isEmpty()) {
                    if (sUrl[1].contains(".")) {
                        filename = sUrl[1];
                    } else if (sUrl[1].contains("?")) {
                        String[] sp2 = sUrl[1].split("\\?");
                        filename = sp2[0].trim()+".pdf";
                    }
                }
            }
            File mediaFile = new File(dir.getPath() + File.separator +
                    filename);

            // download the file
            input = connection.getInputStream();
            output = Files.newOutputStream(mediaFile.toPath());

            byte[] data = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        mProgressDialog.dismiss();
        if (result != null)
            Toast.makeText(context,result, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, R.string.file_downloaded, Toast.LENGTH_LONG).show();
    }

    private File createDir() {
        String appName = context.getString(R.string.app_name_dips);
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), appName);

        return mediaStorageDir;
    }
}
