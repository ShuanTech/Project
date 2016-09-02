package com.shuan.project.asyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.shuan.project.Utils.Common;
import com.shuan.project.employee.JuniorActivity;
import com.shuan.project.employee.SeniorActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class UploadPicture extends AsyncTask<String, String, String> {

    private Context mContext;
    private String path, urlPath, type, from;
    private Common mApp;
    private String s;
    private static JSONObject jsonObj = null;

    public UploadPicture(Context mContext, String path, String type, String from, String url) {
        this.mContext = mContext;
        this.path = path;
        this.type = type;
        this.from = from;
        this.urlPath = url;
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            String fileName = path;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(fileName);

            if (!sourceFile.isFile()) {
                Log.e("uploadFile", "Source File not exist :" + fileName);
                Toast.makeText(mContext, "File Not Found", Toast.LENGTH_SHORT).show();
            } else {

                try {

                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(urlPath);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                    if (serverResponseCode == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = null;
                        StringBuilder sb = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        jsonObj = new JSONObject(sb.toString());
                        s = "succ";
                        Log.i("res", sb.toString());
                    } else {
                        s = "fail";
                    }

                    fileInputStream.close();
                    dos.flush();
                    dos.close();


                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                    Toast.makeText(mContext, "MalformedURLException", Toast.LENGTH_SHORT).show();

                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                } catch (final Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {

        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == "succ") {
            String data = null;
            File file = new File(path);
            file.delete();
            try {
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                JSONObject child = jsonArray.getJSONObject(0);
                data = child.optString("data");
            } catch (Exception e) {
            }
            if (type.equalsIgnoreCase("time_line")) {
                mApp.getPreference().edit().putString(Common.PROPIC, data).commit();
            } else if (type.equalsIgnoreCase("cover")) {
                mApp.getPreference().edit().putString(Common.COVER, data).commit();
            } else if (type.equalsIgnoreCase("resume")) {
                Toast.makeText(mContext, "Applied Successfully", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) mContext;
                if (from.equalsIgnoreCase("senior")) {

                    Intent in = new Intent(mContext, SeniorActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(in);
                    activity.finish();
                } else {
                    Intent in = new Intent(mContext, JuniorActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(in);
                    activity.finish();
                }


            }
        } else {
            Toast.makeText(mContext, "Fail", Toast.LENGTH_SHORT).show();
        }

    }
}
