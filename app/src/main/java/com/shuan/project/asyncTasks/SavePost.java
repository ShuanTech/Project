package com.shuan.Project.asyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class SavePost extends AsyncTask<String, String, String> {

    private ProgressDialog pDialog;
    private Context mContext;
    private String uId, jId, s;
    private ImageView img;
    private HashMap<String, String> sData;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please wait..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    public SavePost(Context mContext, String uId, String jId,ImageView img) {
        this.mContext = mContext;
        this.uId = uId;
        this.jId = jId;
        this.img=img;
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            sData = new HashMap<String, String>();
            sData.put("u_id", uId);
            sData.put("j_id", jId);

            try {
                JSONObject json = Connection.UrlConnection(php.save_post, sData);
                int succ = json.getInt("success");
                if (succ == 2) {
                    s = "already";
                }else if(succ ==1){
                    s = "true";
                }else{
                    s="false";
                }

            } catch (Exception e) {
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
            img.setImageResource(R.drawable.ic_important_clr);
            Toast.makeText(mContext, "Saved Important", Toast.LENGTH_SHORT).show();
        } else if(s.equalsIgnoreCase("false")){
            Toast.makeText(mContext, "Failed Share.Try Again!...", Toast.LENGTH_SHORT).show();
        } else if (s.equalsIgnoreCase("already")){
            AlertDialog.Builder build = new AlertDialog.Builder(mContext);
            build.setCancelable(false);
            build.setTitle("Do you really want remove from Important list ?");
            build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new DelSavedPost(mContext,uId,jId).execute();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();

        }

    }
}
