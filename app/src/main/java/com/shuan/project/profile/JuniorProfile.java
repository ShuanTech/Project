package com.shuan.project.profile;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class JuniorProfile extends AppCompatActivity implements View.OnClickListener {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private CircleImageView usrImg;
    private Common mApp;
    private DisplayImageOptions options;
    private final int GALLERY = 1;
    private final int PHOTO = 2;
    private final int CROP = 3;
    private final int COVER = 4;
    private final int COVERCROP = 5;
    private Uri picUri;
    private File pic;
    private String Path;
    private ImageView coverImg;
    private TextView usrName, education, relation, location, intro, level;
    private ProgressBar progressBar;
    private LinearLayout info, edu, rel, loc;
    private HashMap<String, String> jData;
    private String course, ins_name, status, city, name;
    private String levl;
    private RelativeLayout timeLine, updateInfo, resumeEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junior_profile);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle("");


        //progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        coverImg = (ImageView) findViewById(R.id.cover_img);
        usrImg = (CircleImageView) findViewById(R.id.usr_img);
        usrName = (TextView) findViewById(R.id.name);
        level= (TextView) findViewById(R.id.level);
       /* info = (LinearLayout) findViewById(R.id.info);
        edu = (LinearLayout) findViewById(R.id.edu);
        rel = (LinearLayout) findViewById(R.id.rel);
        loc = (LinearLayout) findViewById(R.id.loc);
        timeLine = (RelativeLayout) findViewById(R.id.time_line);
        updateInfo = (RelativeLayout) findViewById(R.id.update_info);
        resumeEdit = (RelativeLayout) findViewById(R.id.resume_edit);


        intro = (TextView) findViewById(R.id.intro);
        education = (TextView) findViewById(R.id.education);
        relation = (TextView) findViewById(R.id.relation);
        location = (TextView) findViewById(R.id.location);*/


        //usrName.setText(mApp.getPreference().getString(Common.FULLNAME, ""));
        setImage(mApp.getPreference().getString(Common.PROPIC, ""), usrImg);
        setCover(mApp.getPreference().getString(Common.COVER, ""), coverImg);

        usrImg.setOnClickListener(this);
        coverImg.setOnClickListener(this);
       /* timeLine.setOnClickListener(this);
        updateInfo.setOnClickListener(this);
        resumeEdit.setOnClickListener(this);*/


        new JunProfile().execute();

    }

    public class JunProfile extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            jData = new HashMap<String, String>();
            jData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            jData.put("level", mApp.getPreference().getString(Common.LEVEL, ""));

            try {
                JSONObject json = Connection.UrlConnection(php.getJuniorProfile, jData);

                int succ = json.getInt("success");
                if (succ == 0) {
                } else {
                    JSONArray jsonArray = json.getJSONArray("info");

                    JSONObject child = jsonArray.getJSONObject(0);


                    name = child.optString("full_name");
                    levl = child.optString("level");
                    /*course = child.optString("course");
                    ins_name = child.optString("ins_name");
                    status = child.optString("status");
                    city = child.optString("city");*/


                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        usrName.setText(name);
                        level.setText(levl);
                        /*if (mApp.getPreference().getString(Common.INTRO, "").equalsIgnoreCase("")) {
                            intro.setVisibility(View.GONE);
                        } else {
                            intro.setText(mApp.getPreference().getString(Common.INTRO, ""));
                        }

                        if (course != null && !course.trim().isEmpty()) {
                            education.setText("studied " + course + " at " + ins_name);
                        } else {
                            edu.setVisibility(View.GONE);
                        }

                        if (status != null && !status.trim().isEmpty()) {
                            relation.setText(status);
                        } else {
                            rel.setVisibility(View.GONE);
                        }

                        if (city != null && !city.trim().isEmpty()) {
                            location.setText(city);
                        } else {
                            loc.setVisibility(View.GONE);
                        }*/
                    }
                });

            } catch (Exception e) {
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           /* progressBar.setVisibility(View.GONE);
            info.setVisibility(View.VISIBLE);*/
        }
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

    private void setImage(String path, CircleImageView img) {

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.user_white)
                .showImageForEmptyUri(R.drawable.user_white)
                .showImageOnFail(R.drawable.user_white)
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.usr_img:
                loadImg();
                break;
            case R.id.cover_img:
                loadCover();
                break;
          /*  case R.id.time_line:
                //Toast.makeText(getApplicationContext(),"TimeLine",Toast.LENGTH_SHORT).show();
                break;
            case R.id.update_info:
                startActivity(new Intent(getApplicationContext(), JunUpdateInfo.class));
                break;
            case R.id.resume_edit:
                Toast.makeText(getApplicationContext(), "Resume edit", Toast.LENGTH_SHORT).show();
                break;*/
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
        AlertDialog.Builder builder = new AlertDialog.Builder(JuniorProfile.this);
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
                    usrImg.setImageBitmap(photo);

                    if (saveFile(photo)) {
                        new UploadPicture(getApplicationContext(), Path, "time_line","pic", php.uploadProPic).execute();
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
                    coverImg.setImageBitmap(photo);

                    if (saveCover(photo)) {
                        new UploadPicture(getApplicationContext(), Path, "cover", "pic",php.uploadCoverPic).execute();
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
}
