package com.shuan.project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.fragment.EmployerHome;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

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
        pDialog.setMessage("Saving Post...");
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
                if (succ == 0) {
                    s = "false";
                } else if(succ==2){
                    s="Already";
                }else {
                    s = "true";
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
        }else{
            Toast.makeText(mContext, "Already Saved in Important", Toast.LENGTH_SHORT).show();
        }

    }
}
