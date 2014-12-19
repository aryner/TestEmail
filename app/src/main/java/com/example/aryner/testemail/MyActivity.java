package com.example.aryner.testemail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sun.mail.iap.ByteArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity implements RegEmail.RegEmailListener{
    //for full size images?
    static final int REQUEST_TAKE_PHOTO = 1;
    //for full size images?
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final Button send = (Button)findViewById(R.id.button);
        final Button picture = (Button)findViewById(R.id.pictureButton);
        /*
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    new MailService().sendEmail();
                    new AsyncEmail().execute("");
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
                */

 //               /*
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyActivity", "Send button clicked");

                String fromEmail = "vamordortest@gmail.com";
                String fromPassword = "MordorTestEmail";
                String toEmails = "vamordortest@gmail.com";
                List<String> toEmailList = new ArrayList<String>();
                toEmailList.add(toEmails);
                toEmailList.add("alexander.ryner@ucsf.edu");
                String emailSubject = "this is the subject";
                String emailBody = "this is the body";
                new SendMailTask(MyActivity.this).execute(fromEmail, fromPassword, toEmailList, emailSubject, emailBody, MyActivity.this, mCurrentPhotoPath);
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for full size images?
                dispatchTakePictureIntent();
                //end of full size images
                /*
//working for thumbnails
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
//end of working for thumbnails
                */
            }
        });
//        */
        /*
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegEmail email = new RegEmail();
                email.show(getFragmentManager(), "email");
            }
        });
        */
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //make sure there is a camera activity to handle the intent
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //create file where the photo will go
            File photoFile = null;
            try {
                photoFile = createImageFile("testFile");
            } catch (Exception e) {e.printStackTrace();}

            //continue only if the file was successfully created
            if(photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView image = new ImageView(MyActivity.this);
        LinearLayout layout = (LinearLayout)findViewById(R.id.imgLayout);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap map = BitmapFactory.decodeFile(mCurrentPhotoPath,options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        map.compress(Bitmap.CompressFormat.PNG, 100, stream);
        image.setImageBitmap(map);
        layout.addView(image);
//        if(data.getExtras() != null) {
//hopefully send full image
//end of send full image

//already works
/*
//sends thumbnail or stretched thumbnail
            Bitmap map = (Bitmap) data.getExtras().get("data");

            map = Bitmap.createScaledBitmap(map, 600, 970, true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            map.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byte [] byteArray = stream.toByteArray();

            try {
                FileOutputStream fileOut = openFileOutput("testFile", MODE_WORLD_READABLE);
                fileOut.write(byteArray);
                fileOut.close();
            } catch (Exception e) { e.printStackTrace(); }
//end of sends thumbnail
*/
 //       }
    }

    //for full size images?
    private File createImageFile(String fileName) throws IOException {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File storageDir = getExternalFilesDir();
        File image = File.createTempFile(fileName, "", storageDir);
//        File storageDir = new File(getFilesDir()+"/temp");
//        storageDir.mkdirs();
//        File image = File.createTempFile(fileName, "", storageDir);

        mCurrentPhotoPath = "file:"+image.getAbsolutePath();//.substring(1,image.getAbsolutePath().indexOf("-"));
        mCurrentPhotoPath = image.getAbsolutePath();//.substring(1,image.getAbsolutePath().indexOf("-"));
        System.out.println(mCurrentPhotoPath);
        return image;
    }

    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath()+"/.temp/");

        if(!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
