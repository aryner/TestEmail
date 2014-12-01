package com.example.aryner.testemail;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity implements RegEmail.RegEmailListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final Button send = (Button)findViewById(R.id.button);
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
                String emailSubject = "this is the subject";
                String emailBody = "this is the body";
                new SendMailTask(MyActivity.this).execute(fromEmail, fromPassword, toEmailList, emailSubject, emailBody, MyActivity.this);
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
