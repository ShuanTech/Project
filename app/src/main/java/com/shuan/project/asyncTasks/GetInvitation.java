package com.shuan.project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android on 9/10/2016.
 */
public class GetInvitation extends AsyncTask<String, String, String> {

    public Context mContext;
    public String frmId, uId, s;
    public HashMap<String, String> sData;
    public String[] title = new String[0];
    private ProgressDialog pDialog;
    private AlertDialog.Builder builder;
    private String name;

    public GetInvitation(Context mContext, String frmId, String uId) {
        this.mContext = mContext;
        this.frmId = frmId;
        this.uId = uId;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {
        sData = new HashMap<String, String>();
        sData.put("u_id", uId);
        try {

            JSONObject json = Connection.UrlConnection(php.jobTitle, sData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                JSONArray jsonArray = json.getJSONArray("job");
                title = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String tile = child.optString("title");
                    title[i] = tile;

                }
                s = "true";
            }

        } catch (Exception e) {
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if (s.equalsIgnoreCase("true")) {

            final ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();
            builder = new AlertDialog.Builder(mContext);
            builder.setTitle("select the Job to send Invitation");


            builder.setSingleChoiceItems(title, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mSelectedItems.add(which);
                }
            }).setPositiveButton("INVITE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    name = "";
                    for (int i = 0; i < mSelectedItems.size(); i++) {
                        name = title[i];
                    }
                    //Toast.makeText(mContext,name.toString(),Toast.LENGTH_SHORT).show();
                    new Sendinvite(mContext, frmId, uId, name).execute();
                    dialog.cancel();
                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();

        } else {
            Toast.makeText(mContext, "You cannot send invitation until job post", Toast.LENGTH_SHORT).show();
        }
    }
}
