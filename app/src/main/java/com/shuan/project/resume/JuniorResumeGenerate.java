package com.shuan.Project.resume;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.UploadPicture;
import com.shuan.Project.list.ResumeList;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class JuniorResumeGenerate extends AppCompatActivity {

    private static String FILE = Environment.getExternalStorageDirectory() + "/Resume.pdf";
    private Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
    private Font nameFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private Font heading = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font content = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    private Font bulletFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private File file;
    private FileOutputStream fout;
    private HashMap<String, String> resumeData;
    private String name, dob, gen,addr, city,dis,ste,country, pincode, emailId, phNo, fName, mName, mStatus, lang,hobbies,obj;
    private ArrayList<ResumeList> list;
    private ProgressBar progressBar;
    private Common mApp;
    private EditText objective;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_generate);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        list = new ArrayList<ResumeList>();

        if (mApp.getPreference().getBoolean(Common.APPLY, false) == true) {

            FILE = Environment.getExternalStorageDirectory() + "/" + mApp.getPreference().getString(Common.u_id, "") + "-"
                    + getIntent().getStringExtra("job_id") + "-" +
                    getIntent().getStringExtra("refer") + ".pdf";
            chkInComplte();
        } else if (mApp.getPreference().getBoolean("download", false) == true) {
            FILE = Environment.getExternalStorageDirectory() + "/" + mApp.getPreference().getString("name", "") + ".pdf";
            genrateResume("1", FILE);
        } else {
            FILE = Environment.getExternalStorageDirectory() + "/" + mApp.getPreference().getString(Common.u_id, "") + ".pdf";
            chkInComplte();
        }


    }

    private void chkInComplte() {
        if (mApp.getPreference().getBoolean(Common.QUALIFICATION, false) == false) {
            Toast.makeText(getApplicationContext(), "We Need Some Details to Complete your Resume", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UpdateJuniorResumeActivity.class));
            finish();
        } else if (mApp.getPreference().getBoolean(Common.HSC, false) == false) {
            Toast.makeText(getApplicationContext(), "We Need Some Details to Complete your Resume", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UpdateJuniorResumeActivity.class));
            finish();
        } else if (mApp.getPreference().getBoolean(Common.SSLC, false) == false) {
            Toast.makeText(getApplicationContext(), "We Need Some Details to Complete your Resume", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UpdateJuniorResumeActivity.class));
            finish();
        } else if (mApp.getPreference().getBoolean(Common.SKILL, false) == false) {
            Toast.makeText(getApplicationContext(), "We Need Some Details to Complete your Resume", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UpdateJuniorResumeActivity.class));
            finish();
        } else if (mApp.getPreference().getBoolean(Common.HOBBIES, false) == false) {
            Toast.makeText(getApplicationContext(), "We Need Some Details to Complete your Resume", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UpdateJuniorResumeActivity.class));
            finish();
        } else if (mApp.getPreference().getBoolean(Common.PROJECT, false) == false) {
            Toast.makeText(getApplicationContext(), "We Need Some Details to Complete your Resume", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UpdateJuniorResumeActivity.class));
            finish();
        } else if (mApp.getPreference().getBoolean(Common.PERSONALINFO, false) == false) {
            Toast.makeText(getApplicationContext(), "We Need Some Details to Complete your Resume", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UpdateJuniorResumeActivity.class));
            finish();
        } else {

            genrateResume("1", FILE);

        }
    }


    public void genrateResume(String format, String path) {


        if (format.equalsIgnoreCase("1")) {
            jFormatOne(path);
        }


    }


    public void jFormatOne(String path) {
        try {
            file = new File(path);
            fout = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fout);
            doc.open();
            new getInfo().execute();
           /* if (mApp.getPreference().getBoolean("download", false) == true) {
                new getInfo().execute();
            } else {
                if (mApp.getPreference().getBoolean(Common.OBJECTIVE, false) == false) {
                    getObjective();
                } else {

                    //new GetResumeData().execute();
                    new getInfo().execute();
                }
            }*/

        } catch (Exception e) {
        }
    }

    private void getObjective() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.row, null, false);
        objective = (EditText) v.findViewById(R.id.txt1);
        objective.setHint("Objectives");

        AlertDialog.Builder builder = new AlertDialog.Builder(JuniorResumeGenerate.this);
        builder.setView(v);
        builder.setTitle("Objective");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (objective.getText().toString().length() == 0) {
                    objective.setError("Enter Objective");
                } else {
                    objective.setError("");
                    dialog.cancel();
                    mApp.getPreference().edit().putBoolean(Common.OBJECTIVE, true).commit();
                    mApp.getPreference().edit().putString(Common.OBJDATA, objective.getText().toString()).commit();
                    new getInfo().execute();
                }
            }
        }).show();
    }


    public class getInfo extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(final String... params) {
            resumeData = new HashMap<String, String>();
            if (mApp.getPreference().getBoolean("download", false) == true){
                resumeData.put("u_id", mApp.getPreference().getString("Id", ""));
            }else{
                resumeData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            }

            try {
                JSONObject json = Connection.UrlConnection(php.getInfo, resumeData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new getEduInfo().execute();
                        }
                    });

                } else {
                    JSONArray jsonArray = json.getJSONArray("info");
                    JSONObject child = jsonArray.getJSONObject(0);
                    name = child.optString("full_name");
                    dob = child.optString("dob");
                    gen=child.optString("gender");
                    addr = child.optString("address");
                    city = child.optString("city");
                    dis=child.optString("district");
                    ste=child.optString("state");
                    country=child.optString("country");
                    pincode = child.optString("pincode");
                    emailId = child.optString("email_id");
                    phNo = child.optString("ph_no");
                    fName = child.optString("father_name");
                    mName = child.optString("mother_name");
                    mStatus = child.optString("married_status");
                    lang = child.optString("language");
                    hobbies = child.optString("hobbies");
                    obj=child.optString("objective");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Paragraph p1 = new Paragraph(name, nameFont);
                                doc.add(p1);

                                Paragraph p2 = new Paragraph();


                                p2.add(new Paragraph(city + ", " + country, content));
                                p2.add(new Paragraph("EmailId : " + emailId, content));
                                p2.add(new Paragraph("Phone No : " + phNo, content));


                                doc.add(p2);
                                doc.add(Chunk.NEWLINE);
                                doc.add(new LineSeparator(4, 100, BaseColor.BLACK, 0, 0));
                                doc.add(Chunk.NEWLINE);
                                if(obj!=null && !obj.trim().isEmpty()){
                                    Paragraph p3 = new Paragraph("OBJECTIVE", heading);
                                    p3.setAlignment(Paragraph.ALIGN_LEFT);
                                    doc.add(p3);
                                    Paragraph p4 = new Paragraph(obj, content);
                                    p4.setAlignment(Paragraph.ALIGN_CENTER);
                                    doc.add(p4);
                                }

                                doc.add(Chunk.NEWLINE);

                                new getEduInfo().execute();


                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                }

            } catch (Exception e) {
            }

            return null;
        }


    }


    public class getEduInfo extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            list.clear();
            resumeData = new HashMap<String, String>();
            if (mApp.getPreference().getBoolean("download", false) == true){
                resumeData.put("u_id", mApp.getPreference().getString("Id", ""));
            }else{
                resumeData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            }

            try {
                JSONObject json = Connection.UrlConnection(php.getEduInfo, resumeData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new getProjectDetail().execute();
                        }
                    });
                } else {
                    JSONArray jsonArray = json.getJSONArray("edu");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        String concentration = child.optString("concentration");
                        String instname = child.optString("ins_name");
                        String location = child.optString("location");
                        String frm = child.optString("frm_date");
                        String to=child.optString("to_date");
                        String aggregate = child.optString("aggregate");


                        list.add(new ResumeList(concentration, instname, location, frm, to,aggregate));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Paragraph p5 = new Paragraph("EDUCATION", heading);

                                p5.setAlignment(Paragraph.ALIGN_LEFT);
                                doc.add(p5);
                                List p6 = new List(List.UNORDERED, 10);
                                p6.setListSymbol(new Chunk("•", bulletFont));
                                for (int j = 0; j < list.size(); j++) {
                                    ResumeList currItem = list.get(j);
                                    p6.add(new ListItem(currItem.getOrgName() + " at " + currItem.getPos() + ", " + currItem.getLoc() +" during "+currItem.getFrm_dat()+" - "+ currItem.getTo_dat()+" with " + currItem.getStus() + " %"));
                                }

                                doc.add(p6);
                                doc.add(Chunk.NEWLINE);


                                new getProjectDetail().execute();

                            } catch (Exception e) {
                            }

                        }
                    });
                }

            } catch (Exception e) {
            }
            return null;
        }

    }

    public class getSkill extends AsyncTask<String, String, String> {
        String lang_known, dev, interest, others;

        @Override
        protected String doInBackground(String... params) {
            resumeData = new HashMap<String, String>();
            if (mApp.getPreference().getBoolean("download", false) == true){
                resumeData.put("u_id", mApp.getPreference().getString("Id", ""));
            }else{
                resumeData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            }

            try {

                JSONObject json = Connection.UrlConnection(php.getSkill, resumeData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new getCertification().execute();
                        }
                    });

                } else {
                    JSONArray jsonArray = json.getJSONArray("skill");
                    JSONObject child = jsonArray.getJSONObject(0);
                    lang_known = child.optString("lang_known");

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Paragraph p7 = new Paragraph("SKILLS", heading);
                            p7.setAlignment(Paragraph.ALIGN_LEFT);
                            doc.add(p7);
                            List p8 = new List(List.UNORDERED, 10);
                            p8.setListSymbol(new Chunk("•", bulletFont));
                            p8.add(new ListItem(lang_known));
                            doc.add(p8);
                            doc.add(Chunk.NEWLINE);


                            new getCertification().execute();
                        } catch (Exception e) {
                        }
                    }
                });

            } catch (Exception e) {
            }
            return null;
        }
    }

    public class getProjectDetail extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            list.clear();
            resumeData = new HashMap<String, String>();
            if (mApp.getPreference().getBoolean("download", false) == true){
                resumeData.put("u_id", mApp.getPreference().getString("Id", ""));
                resumeData.put("level","senior");
            }else{
                resumeData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
                resumeData.put("level","senior");
            }


            try {
                JSONObject json = Connection.UrlConnection(php.getProjectDetail, resumeData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new getSkill().execute();
                        }
                    });
                } else {
                    final JSONArray jsonArray = json.getJSONArray("project");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        String title = child.optString("p_title");
                        String desc = child.optString("p_description");


                        list.add(new ResumeList(title,desc));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                Paragraph p14 = new Paragraph("PROJECT SUMMARY", heading);
                                p14.setAlignment(Paragraph.ALIGN_LEFT);
                                doc.add(p14);


                                for (int j = 0; j < list.size(); j++) {
                                    ResumeList curr = list.get(j);

                                    Paragraph p15=new Paragraph(curr.getCerName()+" : ",heading);
                                    doc.add(p15);
                                    Paragraph p16=new Paragraph(curr.getCerCentre(),content);
                                    p16.setAlignment(Element.ALIGN_LEFT);
                                    p16.setIndentationLeft(20);
                                    doc.add(p16);
                                    //doc.add(Chunk.NEWLINE);
                                }
                                doc.add(Chunk.NEWLINE);


                                new getSkill().execute();

                            } catch (Exception e) {
                            }
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

        }
    }


    public class getCertification extends AsyncTask<String, String, String> {
        String cName, cCentre;

        @Override
        protected String doInBackground(String... params) {
            list.clear();
            resumeData = new HashMap<String, String>();
            if (mApp.getPreference().getBoolean("download", false) == true){
                resumeData.put("u_id", mApp.getPreference().getString("Id", ""));
            }else{
                resumeData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            }

            try {

                JSONObject json = Connection.UrlConnection(php.getCertify, resumeData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            new getAchivmnt().execute();

                        }
                    });
                } else {

                    JSONArray jsonArray = json.getJSONArray("certify");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        cName = child.optString("cer_name");
                        cCentre = child.optString("cer_centre");
                        String dur=child.optString("cer_dur");

                        list.add(new ResumeList(cName, cCentre,dur));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Paragraph p8 = new Paragraph("CERTIFICATION", heading);

                                p8.setAlignment(Paragraph.ALIGN_LEFT);
                                doc.add(p8);
                                List p9 = new List(List.UNORDERED, 10);
                                p9.setListSymbol(new Chunk("•", bulletFont));
                                for (int j = 0; j < list.size(); j++) {
                                    ResumeList currItem = list.get(j);
                                    p9.add(new ListItem("\" "+currItem.getCerName() + " \""+" from " + currItem.getCerCentre()+", "+currItem.getDur()+" months."));

                                }

                                doc.add(p9);
                                doc.add(Chunk.NEWLINE);


                                new getAchivmnt().execute();

                            } catch (Exception e) {
                            }
                        }
                    });
                }

            } catch (Exception e) {
            }
            return null;
        }
    }

    public class getAchivmnt extends AsyncTask<String, String, String> {
        String a_name;

        @Override
        protected String doInBackground(String... params) {
            list.clear();
            resumeData = new HashMap<String, String>();
            if (mApp.getPreference().getBoolean("download", false) == true) {
                resumeData.put("u_id", mApp.getPreference().getString("Id", ""));
            } else {
                resumeData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            }
            try {

                JSONObject json = Connection.UrlConnection(php.getAchivmnt, resumeData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new getCurricular().execute();
                        }
                    });
                } else {

                    JSONArray jsonArray = json.getJSONArray("achivmnt");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        a_name = child.optString("a_name");

                        list.add(new ResumeList(a_name));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Paragraph p10 = new Paragraph("ACHIEVEMENT", heading);

                                p10.setAlignment(Paragraph.ALIGN_LEFT);
                                doc.add(p10);
                                List p11 = new List(List.UNORDERED, 10);
                                p11.setListSymbol(new Chunk("•", bulletFont));
                                for (int j = 0; j < list.size(); j++) {
                                    ResumeList currItem = list.get(j);
                                    p11.add(new ListItem(currItem.getaName()));

                                }
                                doc.add(p11);
                                doc.add(Chunk.NEWLINE);
                                new getCurricular().execute();

                            } catch (Exception e) {
                            }
                        }
                    });
                }

            } catch (Exception e) {
            }
            return null;
        }
    }

    public class getCurricular extends AsyncTask<String, String, String> {
        String e_name;

        @Override
        protected String doInBackground(String... params) {
            list.clear();
            resumeData = new HashMap<String, String>();
            if (mApp.getPreference().getBoolean("download", false) == true) {
                resumeData.put("u_id", mApp.getPreference().getString("Id", ""));
            } else {
                resumeData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            }
            try {

                JSONObject json = Connection.UrlConnection(php.getExtracurricular, resumeData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            personalInfo();
                        }
                    });
                } else {

                    JSONArray jsonArray = json.getJSONArray("curricular");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        e_name = child.optString("extra");

                        list.add(new ResumeList(e_name));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Paragraph p12 = new Paragraph("EXTRA CURRICULAR ACTIVITIES", heading);

                                p12.setAlignment(Paragraph.ALIGN_LEFT);
                                doc.add(p12);
                                List p13 = new List(List.UNORDERED, 10);
                                p13.setListSymbol(new Chunk("•", bulletFont));
                                for (int j = 0; j < list.size(); j++) {
                                    ResumeList currItem = list.get(j);
                                    p13.add(new ListItem(currItem.getaName()));

                                }
                                doc.add(p13);
                                doc.add(Chunk.NEWLINE);

                                personalInfo();

                            } catch (Exception e) {
                            }
                        }
                    });
                }

            } catch (Exception e) {
            }
            return null;
        }
    }


    private void personalInfo() {
        try {

            if (fName != null && !fName.trim().isEmpty() || mName != null && !mName.trim().isEmpty() || dob != null && !dob.trim().isEmpty()
                    || lang != null && !lang.trim().isEmpty() || hobbies != null && !hobbies.trim().isEmpty() || gen!=null && !gen.trim().isEmpty()
                    || mStatus!=null && !mStatus.trim().isEmpty()) {

                Paragraph p16 = new Paragraph("PERSONAL INFO", heading);
                p16.setAlignment(Paragraph.ALIGN_LEFT);
                doc.add(p16);

                PdfPTable table1 = new PdfPTable(2);

                if (fName != null && !fName.trim().isEmpty()) {
                    PdfPCell c11 = new PdfPCell(new Paragraph("Father Name ", heading));
                    PdfPCell c12 = new PdfPCell(new Paragraph(fName, content));
                    c11.setBorder(Rectangle.NO_BORDER);
                    c11.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    c12.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(c11);
                    table1.addCell(c12);
                }

                if (mName != null && !mName.trim().isEmpty()) {
                    PdfPCell c13 = new PdfPCell(new Paragraph("Mother Name", heading));
                    PdfPCell c14 = new PdfPCell(new Paragraph(mName, content));
                    c13.setBorder(Rectangle.NO_BORDER);
                    c13.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    c14.setBorder(Rectangle.NO_BORDER);

                    table1.addCell(c13);
                    table1.addCell(c14);
                }

                if (dob != null && !dob.trim().isEmpty()) {
                    PdfPCell c15 = new PdfPCell(new Paragraph("Date of Birth", heading));
                    PdfPCell c16 = new PdfPCell(new Paragraph(dob, content));

                    c15.setBorder(Rectangle.NO_BORDER);
                    c15.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    c16.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(c15);
                    table1.addCell(c16);
                }

                if(gen!=null && !gen.trim().isEmpty()){
                    PdfPCell c26 = new PdfPCell(new Paragraph("Gender", heading));
                    PdfPCell c27 = new PdfPCell(new Paragraph(gen, content));

                    c26.setBorder(Rectangle.NO_BORDER);
                    c26.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    c27.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(c26);
                    table1.addCell(c27);
                }

                if(mStatus!=null && !mStatus.trim().isEmpty()){
                    PdfPCell c28 = new PdfPCell(new Paragraph("Martial Status", heading));
                    PdfPCell c29 = new PdfPCell(new Paragraph(mStatus, content));

                    c28.setBorder(Rectangle.NO_BORDER);
                    c28.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    c29.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(c28);
                    table1.addCell(c29);
                }


                if (lang != null && !lang.trim().isEmpty()) {
                    PdfPCell c17 = new PdfPCell(new Paragraph("Languages Known", heading));
                    PdfPCell c18 = new PdfPCell(new Paragraph(lang, content));
                    c17.setBorder(Rectangle.NO_BORDER);
                    c17.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    c18.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(c17);
                    table1.addCell(c18);
                }

                if (hobbies != null && !hobbies.trim().isEmpty()) {
                    PdfPCell c19 = new PdfPCell(new Paragraph("Hobbies", heading));
                    PdfPCell c20 = new PdfPCell(new Paragraph(hobbies, content));

                    c19.setBorder(Rectangle.NO_BORDER);
                    c19.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    c20.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(c19);
                    table1.addCell(c20);
                }

                if(addr!=null && !addr.trim().isEmpty()){
                    PdfPCell c19 = new PdfPCell(new Paragraph("Address", heading));
                    PdfPCell c20 = new PdfPCell(new Paragraph(addr+"\n"+city+" - "+pincode+"\n"+dis+", "+ste+"\n"+country+".", content));

                    c19.setBorder(Rectangle.NO_BORDER);
                    c19.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    c20.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(c19);
                    table1.addCell(c20);
                }

                table1.setHorizontalAlignment(Element.ALIGN_CENTER);
                doc.add(table1);

                doc.add(Chunk.NEWLINE);

                doc.add(Chunk.NEWLINE);
                doc.add(Chunk.NEWLINE);
                if (city != null && !city.trim().isEmpty()) {
                    Paragraph p19 = new Paragraph("PLACE:  " + city);
                    p19.setAlignment(Paragraph.ALIGN_LEFT);
                    doc.add(p19);
                    doc.add(Chunk.NEWLINE);
                }
            }

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            Paragraph p20 = new Paragraph("DATE:  " + format.format(date));
            p20.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p20);
            Paragraph p21 = new Paragraph(name);
            p21.setAlignment(Paragraph.ALIGN_RIGHT);
            doc.add(p21);
            doc.close();


            if (mApp.getPreference().getBoolean(Common.APPLY, false) == true) {
                Toast.makeText(getApplicationContext(), "Upload start", Toast.LENGTH_SHORT).show();
                new UploadPicture(JuniorResumeGenerate.this, FILE, "resume", "senior", php.upload_resume).execute();
                mApp.getPreference().edit().putBoolean(Common.APPLY, false).commit();
            } else if (mApp.getPreference().getBoolean("download", false) == true) {
                mApp.getPreference().edit().putBoolean("downlaod", false).commit();
                Toast.makeText(getApplicationContext(), "Resume Saved in" + FILE, Toast.LENGTH_SHORT).show();
                openPdf();
            } else {
                Toast.makeText(getApplicationContext(), "Resume Saved in" + FILE, Toast.LENGTH_SHORT).show();
                openPdf();
            }


        } catch (Exception e) {
        }
    }


    private void openPdf() {
        File pdfFile = new File(FILE);
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pdfIntent);
        finish();
    }


}
