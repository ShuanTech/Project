package com.shuan.Project.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.Toast;

import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class PostCommnts extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, jId, cmnts, s;
    private ImageButton but;
    private HashMap<String, String> cData;

    public PostCommnts(Context mContext, String uId, String jId, String cmnts,ImageButton but) {
        this.mContext = mContext;
        this.uId = uId;
        this.jId=jId;
        this.cmnts = cmnts;
        this.but=but;
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();
        cData.put("u_id", uId);
        cData.put("j_id", jId);
        cData.put("cmts", cmnts);
        try {

            JSONObject json = Connection.UrlConnection(php.postComment, cData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                s = "true";
            }

        } catch (Exception e) {
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equalsIgnoreCase("true")) {
            but.setEnabled(true);
            Toast.makeText(mContext, "Comment Successfully Post", Toast.LENGTH_SHORT).show();
        } else {
            but.setEnabled(true);
            Toast.makeText(mContext, "Try Again.Comment not post", Toast.LENGTH_SHORT).show();
        }
    }
}
