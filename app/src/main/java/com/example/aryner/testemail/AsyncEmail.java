package com.example.aryner.testemail;

import android.os.AsyncTask;

/**
 * Created by aryner on 12/1/14.
 */
public class AsyncEmail extends AsyncTask<String, Void, Void> {
    private  Exception exception;

    protected Void doInBackground(String... stuff) {
        try {
            new MailService().sendEmail();
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }
}
