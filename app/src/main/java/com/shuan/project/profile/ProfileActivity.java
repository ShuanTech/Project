package com.shuan.project.profile;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.project.R;
import com.shuan.project.Utils.CircleImageView;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.UploadPicture;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.resume.ResumeEditActivity;
import com.shuan.project.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Common mApp;
    private String u_id, level;
    private ImageView cover;
    private CircleImageView proPic;
    private TextView name, position, org, intro;
    private Button bu1, bu2;
    private TextView abt, url, cate, sze, found;
    private ProgressBar progressBar;
    private RelativeLayout scroll;
    private HashMap<String, String> pData;
    private DisplayImageOptions options;
    private ArrayList<Sample> list;
    private LinearLayout noData, cmpny, employee, exprience, exp, education, edut, skill, skll, project, prjct, contact;
    private TextView mail, phNo;
    private String pro_pic, cover_pic, cmpny_name, c_type, landmark, country, year_of_establish, num_wrkers, c_desc, c_website, follow;
    private final int GALLERY = 1;
    private final int PHOTO = 2;
    private final int CROP = 3;
    private final int COVER = 4;
    private final int COVERCROP = 5;
    private Uri picUri;
    private File pic;
    private String Path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mApp = (Common) getApplicationContext();

        mApp.getPreference().edit().putBoolean("start",true).commit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setBackgroundColor(Color.TRANSPARENT);


        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (RelativeLayout) findViewById(R.id.scroll);
        cover = (ImageView) findViewById(R.id.cover_img);
        proPic = (CircleImageView) findViewById(R.id.pro_img);
        name = (TextView) findViewById(R.id.name);
        position = (TextView) findViewById(R.id.position);
        org = (TextView) findViewById(R.id.company_name);
        bu1 = (Button) findViewById(R.id.but1);
        bu2 = (Button) findViewById(R.id.but2);
        noData = (LinearLayout) findViewById(R.id.no_data);
        cmpny = (LinearLayout) findViewById(R.id.cmpny);
        employee = (LinearLayout) findViewById(R.id.employee);
        exp = (LinearLayout) findViewById(R.id.exp);
        education = (LinearLayout) findViewById(R.id.education);
        edut = (LinearLayout) findViewById(R.id.edu);
        skill = (LinearLayout) findViewById(R.id.skill);
        skll = (LinearLayout) findViewById(R.id.skll);
        project = (LinearLayout) findViewById(R.id.project);
        prjct = (LinearLayout) findViewById(R.id.prjct);
        contact = (LinearLayout) findViewById(R.id.contact);
        mail = (TextView) findViewById(R.id.mail);
        phNo = (TextView) findViewById(R.id.ph_no);
        abt = (TextView) findViewById(R.id.abt);
        url = (TextView) findViewById(R.id.url);
        cate = (TextView) findViewById(R.id.cate);
        sze = (TextView) findViewById(R.id.sze);
        found = (TextView) findViewById(R.id.found);
        exprience = (LinearLayout) findViewById(R.id.exprience);

        list = new ArrayList<Sample>();

        new profileView().execute();

        cover.setOnClickListener(this);
        proPic.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cover_img:
                loadCover();
                break;
            case R.id.pro_img:
                loadImg();
                break;
        }

    }

    private void loadCover() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("return-data", true);
        startActivityForResult(photoPickerIntent, COVER);
    }

    private void loadImg() {

        final String[] action = {"From Camera", "From Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Select Picture Using");
        builder.setItems(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int itm) {
                if (action[itm].equalsIgnoreCase("From Camera")) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    pic = new File(Environment.getExternalStorageDirectory(),
                            mApp.getPreference().getString(Common.u_id, "") + ".jpg");

                    picUri = Uri.fromFile(pic);

                    cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, picUri);

                    cameraIntent.putExtra("return-data", true);
                    startActivityForResult(cameraIntent, PHOTO);

                } else {

                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    photoPickerIntent.setType("image/*");
                    photoPickerIntent.putExtra("return-data", true);
                    startActivityForResult(photoPickerIntent, GALLERY);
                }

            }
        }).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PHOTO && resultCode == RESULT_OK) {
            CropImage();
        } else if (requestCode == GALLERY) {

            if (data != null) {
                picUri = data.getData();
                CropImage();
            }
        } else if (requestCode == CROP) {
            try {

                if (data != null) {

                    Bundle extras = data.getExtras();
                    // get the cropped bitmap
                    Bitmap photo = extras.getParcelable("data");
                    proPic.setImageBitmap(photo);

                    if (saveFile(photo)) {
                        new UploadPicture(getApplicationContext(), Path, "time_line", "pic", php.uploadProPic).execute();
                    }
                }
            } catch (Exception e) {
            }

        } else if (requestCode == COVER) {
            if (data != null) {
                picUri = data.getData();
                CropCover();
            }
        } else if (requestCode == COVERCROP) {
            try {

                if (data != null) {

                    Bundle extras = data.getExtras();
                    // get the cropped bitmap
                    Bitmap photo = extras.getParcelable("data");
                    cover.setImageBitmap(photo);

                    if (saveCover(photo)) {
                        new UploadPicture(getApplicationContext(), Path, "cover", "pic", php.uploadCoverPic).execute();
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private boolean saveCover(Bitmap photo) {
        Bitmap b2 = null;
        File destination;
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f, 0.5f);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b2 = Bitmap.createScaledBitmap(photo, 800, 300, false);
        b2.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        destination = new File(Environment.getExternalStorageDirectory(), mApp.getPreference().getString(Common.u_id, "") + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path = destination.getAbsolutePath();
        if (Path != null) {
            return true;
        } else {
            return false;
        }
    }


    private void CropCover() {
        try {

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(picUri, "image/*");

            intent.putExtra("crop", "true");
            intent.putExtra("outputX", 900);
            intent.putExtra("outputY", 273);
            intent.putExtra("aspectX", 2);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scaleUpIfNeeded", true);

            intent.putExtra("return-data", true);

            startActivityForResult(intent, COVERCROP);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Your device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean saveFile(Bitmap photo) {
        Bitmap b2 = null;
        File destination;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b2 = Bitmap.createScaledBitmap(photo, 300, 300, false);
        b2.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        destination = new File(Environment.getExternalStorageDirectory(), mApp.getPreference().getString(Common.u_id, "") + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path = destination.getAbsolutePath();
        if (Path != null) {
            return true;
        } else {
            return false;
        }
    }

    private void CropImage() {
        try {

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(picUri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("aspectX", 4);
            intent.putExtra("aspectY", 4);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, CROP);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Your device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }


    public class profileView extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            pData = new HashMap<String, String>();
            pData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            pData.put("level", mApp.getPreference().getString(Common.LEVEL, ""));


            try {

                JSONObject json = Connection.UrlConnection(php.profile, pData);

                int succ = json.getInt("success");

                if (succ == 0) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noData.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {

                        JSONArray jsonArray = json.getJSONArray("profile");
                        JSONObject child = jsonArray.getJSONObject(0);

                        JSONObject info = child.getJSONObject("info");
                        JSONArray infoArray = info.getJSONArray("info");
                        JSONObject data = infoArray.getJSONObject(0);
                        final String fullname = data.optString("full_name");
                        final String email_id = data.optString("email_id");
                        final String ph_no = data.optString("ph_no");
                        final String pro_pic = data.optString("pro_pic");
                        final String cover_pic = data.optString("cover_pic");

                        JSONObject cntInfo = child.getJSONObject("cnt");
                        JSONArray cntArray = cntInfo.getJSONArray("cnt");
                        JSONObject data1 = cntArray.getJSONObject(0);
                        final String city = data1.optString("city");
                        final String country = data1.optString("country");


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                contact.setVisibility(View.VISIBLE);
                                name.setText(fullname);
                                if (!city.equalsIgnoreCase("")) {
                                    position.setText(city + " ," + country);
                                }
                                mail.setText(email_id);
                                phNo.setText(ph_no);
                            }
                        });

                        final JSONObject edu = child.getJSONObject("edu");
                        JSONArray eduArray = edu.getJSONArray("edu");

                        for (int i = 0; i < eduArray.length(); i++) {
                            JSONObject data2 = eduArray.getJSONObject(i);

                            final String concentration = data2.optString("concentration");
                            final String ins_name = data2.optString("ins_name");
                            final String location = data2.optString("location");
                            final String frmDat = data2.optString("frmDat");
                            final String toDat = data2.optString("toDat");

                            if (!concentration.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        education.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.degree);
                                        txt.setText(concentration + "\n" + ins_name + " , " + location + "\n" + frmDat + " - " + toDat);
                                        edut.addView(v);
                                    }
                                });
                            }


                        }

                        JSONObject skills = child.getJSONObject("skill");
                        JSONArray skillArray = skills.getJSONArray("skill");
                        for (int i = 0; i < skillArray.length(); i++) {
                            JSONObject data3 = skillArray.getJSONObject(i);
                            final String skls = data3.optString("skill");

                            if (!skls.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        skill.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setVisibility(View.GONE);
                                        txt.setText(skls);
                                        skll.addView(v);
                                    }
                                });
                            }
                        }

                        JSONObject projects = child.getJSONObject("project");
                        JSONArray pjctArray = projects.getJSONArray("project");
                        for (int i = 0; i < pjctArray.length(); i++) {
                            JSONObject data4 = pjctArray.getJSONObject(i);
                            final String p_title = data4.optString("p_title");

                            if (!p_title.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        project.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setVisibility(View.GONE);
                                        txt.setText(p_title);
                                        prjct.addView(v);
                                    }
                                });
                            }
                        }
                    } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {


                        JSONArray jsonArray = json.getJSONArray("profile");
                        JSONObject child = jsonArray.getJSONObject(0);

                        JSONObject info = child.getJSONObject("info");
                        JSONArray infoArray = info.getJSONArray("info");
                        JSONObject data = infoArray.getJSONObject(0);
                        final String fullname = data.optString("full_name");
                        final String email_id = data.optString("email_id");
                        final String ph_no = data.optString("ph_no");
                        final String pro_pic = data.optString("pro_pic");
                        final String cover_pic = data.optString("cover_pic");

                        JSONObject cntInfo = child.getJSONObject("cnt");
                        JSONArray cntArray = cntInfo.getJSONArray("cnt");
                        JSONObject data1 = cntArray.getJSONObject(0);
                        final String org_name = data1.optString("org_name");
                        final String pos = data1.optString("position");
                        final String loc = data1.optString("location");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                contact.setVisibility(View.VISIBLE);
                                name.setText(fullname);
                                if (!org_name.equalsIgnoreCase("")) {
                                    position.setText(pos);
                                    org.setText(org_name + " ," + loc);
                                }
                                mail.setText(email_id);
                                phNo.setText(ph_no);

                            }
                        });

                        JSONObject wrk = child.optJSONObject("wrk");
                        JSONArray wrkArray = wrk.getJSONArray("wrk");

                        for (int i = 0; i < wrkArray.length(); i++) {
                            JSONObject data6 = wrkArray.getJSONObject(i);
                            final String orgName = data6.optString("org_name");
                            final String poss = data6.optString("position");
                            final String locc = data6.optString("location");
                            final String frmDat = data6.optString("frmDat");
                            final String toDat = data6.optString("toDat");

                            if (!orgName.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        exprience.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.ic_work);
                                        txt.setText(poss + "\n" + orgName + " , " + locc + "\n" + frmDat + " - " + toDat);
                                        exp.addView(v);
                                    }
                                });
                            }
                        }

                        final JSONObject edu = child.getJSONObject("edu");
                        JSONArray eduArray = edu.getJSONArray("edu");

                        for (int i = 0; i < eduArray.length(); i++) {
                            JSONObject data2 = eduArray.getJSONObject(i);

                            final String concentration = data2.optString("concentration");
                            final String ins_name = data2.optString("ins_name");
                            final String location = data2.optString("location");
                            final String frmDat = data2.optString("frmDat");
                            final String toDat = data2.optString("toDat");

                            if (!concentration.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        education.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.degree);
                                        txt.setText(concentration + "\n" + ins_name + " , " + location + "\n" + frmDat + " - " + toDat);
                                        edut.addView(v);
                                    }
                                });
                            }


                        }

                        JSONObject skills = child.getJSONObject("skill");
                        JSONArray skillArray = skills.getJSONArray("skill");
                        for (int i = 0; i < skillArray.length(); i++) {
                            JSONObject data3 = skillArray.getJSONObject(i);
                            final String skls = data3.optString("skill");

                            if (!skls.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        skill.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setVisibility(View.GONE);
                                        txt.setText(skls);
                                        skll.addView(v);
                                    }
                                });
                            }
                        }

                        JSONObject projects = child.getJSONObject("project");
                        JSONArray pjctArray = projects.getJSONArray("project");
                        for (int i = 0; i < pjctArray.length(); i++) {
                            JSONObject data4 = pjctArray.getJSONObject(i);
                            final String p_title = data4.optString("p_title");

                            if (!p_title.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        project.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setVisibility(View.GONE);
                                        txt.setText(p_title);
                                        prjct.addView(v);
                                    }
                                });
                            }
                        }
                    } else {


                        JSONArray jsonArray = json.getJSONArray("profile");
                        JSONObject child = jsonArray.getJSONObject(0);

                        JSONObject info = child.getJSONObject("info");
                        JSONArray infoArray = info.getJSONArray("info");
                        JSONObject data = infoArray.getJSONObject(0);
                        pro_pic = data.optString("pro_pic");
                        cover_pic = data.optString("cover_pic");
                        cmpny_name = data.optString("cmpny_name");
                        c_type = data.optString("c_type");
                        landmark = data.optString("landmark");
                        country = data.optString("country");
                        year_of_establish = data.optString("year_of_establish");
                        num_wrkers = data.optString("num_wrkers");
                        c_desc = data.optString("c_desc");
                        c_website = data.optString("c_website");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cmpny.setVisibility(View.VISIBLE);
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                name.setText(cmpny_name);
                                position.setText(c_type);
                                org.setText(landmark + "," + country);
                                abt.setText(c_desc);
                                url.setText(c_website);
                                cate.setText(c_type);
                                sze.setText(num_wrkers);
                                found.setText(year_of_establish);

                            }
                        });


                    }


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


    private void setImage(String path, CircleImageView img) {

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.user)
                .showImageForEmptyUri(R.drawable.user)
                .showImageOnFail(R.drawable.user)
                .build();

        ImageLoader.getInstance().displayImage(path, img, options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {

            }
        });
    }


    private void setCover(String path, ImageView img) {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.header)
                .showImageForEmptyUri(R.drawable.header)
                .showImageOnFail(R.drawable.header)
                .build();

        ImageLoader.getInstance().displayImage(path, img, options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        MenuItem item = menu.findItem(R.id.edit);
        if(mApp.getPreference().getString(Common.LEVEL,"").equalsIgnoreCase("3")){
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                startActivity(new Intent(getApplicationContext(), ResumeEditActivity.class));
                break;
            case R.id.search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
