package com.example.myself.findme.network;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.myself.findme.util.CallBackInterface;
import com.example.myself.findme.util.Network;

import org.json.JSONException;

import java.io.IOException;


// extending AsyncTask (Asynchronous Task) a in build thread class to handle network operation
public class Background_Network extends AsyncTask<String,String,String> {

  public  ProgressDialog progressDialog;
    android.support.v4.app.Fragment context;
  public Background_Network(android.support.v4.app.Fragment context)
    {
        this.context=context;
    }

    // network operation
    @Override
    protected String doInBackground(String... params) {
        try {
            return Network.getJson(params[0]); // params[0] - url
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // after network operation
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //
        try {
            ((CallBackInterface)context).parsing(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // pre execute - operation before network connection
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  progress dialog visible
        progressDialog =new ProgressDialog(context.getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("please wait");
        progressDialog.show();

    }


}
