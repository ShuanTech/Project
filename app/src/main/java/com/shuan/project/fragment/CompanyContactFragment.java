package com.shuan.Project.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyContactFragment extends Fragment {

    private Common mApp;
    private Context mContext;
    private HashMap<String, String> sData;
    private TextView cName, cMail, cPh, cTm, cAddr, cLoc, cCntry, cPhNo, cMailId, cWeb;
    private ProgressBar progressBar;
    private RelativeLayout scroll;


    public CompanyContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        View v = inflater.inflate(R.layout.fragment_company_contact, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        scroll = (RelativeLayout) v.findViewById(R.id.scroll);

        cName = (TextView) v.findViewById(R.id.c_name);
        cMail = (TextView) v.findViewById(R.id.c_mail);
        cPh = (TextView) v.findViewById(R.id.c_ph);
        cTm = (TextView) v.findViewById(R.id.c_tm);
        cAddr = (TextView) v.findViewById(R.id.c_addr);
        cLoc = (TextView) v.findViewById(R.id.c_loc);
        cCntry = (TextView) v.findViewById(R.id.c_cntry);
        cPhNo = (TextView) v.findViewById(R.id.c_ph_no);
        cMailId = (TextView) v.findViewById(R.id.c_mail_id);
        cWeb = (TextView) v.findViewById(R.id.c_web);

        new GetCompanyContact().execute();
        return v;
    }

    public class GetCompanyContact extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            sData = new HashMap<String, String>();
            sData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            try {

                JSONObject json = Connection.UrlConnection(php.cmpnyCnt, sData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "No Contact Info", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    JSONArray jsonArray = json.getJSONArray("contact");
                    final JSONObject child = jsonArray.getJSONObject(0);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cName.setText(child.optString("contact_person"));
                            cPh.setText(child.optString("contact_ph"));
                            cMail.setText(child.optString("contact_mail"));
                            cTm.setText(child.optString("contact_time"));
                            cAddr.setText(child.optString("addr"));
                            cLoc.setText(child.optString("landmark"));
                            cCntry.setText(child.optString("country"));
                            cPhNo.setText(child.optString("ph_no") + "\n" + child.optString("alt_no"));
                            cMailId.setText(child.optString("mail") + "\n" + child.optString("alt_mail"));
                            cWeb.setText(child.optString("c_website"));
                        }
                    });
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
        }
    }

}
