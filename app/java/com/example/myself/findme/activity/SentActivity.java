package com.example.myself.findme.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myself.findme.R;
import com.example.myself.findme.adapter.MessageAdapter;
import com.example.myself.findme.adapter.MessageModel;
import com.example.myself.findme.network.Background_Network_Class;
import com.example.myself.findme.util.CallBackInterface;
import com.example.myself.findme.util.ConnectionDetector;
import com.example.myself.findme.util.EndPoints;
import com.example.myself.findme.util.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentActivity extends AppCompatActivity implements CallBackInterface, MessageAdapter.ClickListener {

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    Background_Network_Class background_network;
    List<MessageModel> inboxmessagelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.inbox_list);

        // if connection available
        if (new ConnectionDetector(this).isConnectingToInternet()) {
            background_network = new Background_Network_Class(this);
            String userid = PrefUtils.getValue(this,"app_pref","userid");
            background_network.execute(EndPoints.SENT_MESSAGE + "?user_id="+userid);
        } else {
            Toast.makeText(this, "network not available", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void parsing(String s) throws JSONException {


        JSONObject jsonObject = new JSONObject(s);

        int success = jsonObject.getInt("success");

        if (success == 1) {

            JSONArray arrayhotel = jsonObject.getJSONArray("inbox");

            inboxmessagelist = new ArrayList<>();

            for (int i = 0; i < arrayhotel.length(); i++) {
                JSONObject jsonObject1 = arrayhotel.getJSONObject(i);

                String mid = jsonObject1.getString("mid");
                String ufrom = jsonObject1.getString("ufrom");
                String uto = jsonObject1.getString("uto");
                String message = jsonObject1.getString("message");
                String category = jsonObject1.getString("category");
                String productid = jsonObject1.getString("product_id");
                String created_at = jsonObject1.getString("created_at");


                String name = jsonObject1.getString("name");


                MessageModel messageModel = new MessageModel(mid,ufrom,uto,message,category,created_at,productid,name);



                inboxmessagelist.add(messageModel);
            }

            llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            MessageAdapter ca = new MessageAdapter(this,this,inboxmessagelist);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(ca);

            background_network.progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(final int index) {

        new MaterialDialog.Builder(SentActivity.this)
                .title("Message")
                .content(inboxmessagelist.get(index).getMessage())
                .positiveText("Reply")
                .negativeText("Exit")

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        new MaterialDialog.Builder(SentActivity.this)
                                .title("Compose")
                                .customView(R.layout.custom_message, true)
                                .positiveText("Submit")
                                .negativeText("Exit")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        final EditText etfeedback = (EditText) dialog.findViewById(R.id.input_message);

                                        if (etfeedback.getText().toString() != null || etfeedback.getText().toString() != "") {

                                            StringRequest strReq = new StringRequest(Request.Method.POST,
                                                    EndPoints.SENT_MESSAGE_DETAIL, new Response.Listener<String>() {

                                                @Override
                                                public void onResponse(String response) {
                                                    Log.e("post", "response: " + response);

                                                    try {
                                                        JSONObject obj = new JSONObject(response);

                                                        // check for error
                                                        if (obj.getInt("success") == 1) {

                                                            Toast.makeText(SentActivity.this, "successfully sent message", Toast.LENGTH_SHORT).show();

                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "" + obj.getString("message"), Toast.LENGTH_LONG).show();
                                                        }

                                                    } catch (JSONException e) {
                                                        Log.e("post", "json parsing error: " + e.getMessage());
                                                        Toast.makeText(getApplicationContext(), "json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    NetworkResponse networkResponse = error.networkResponse;
                                                    Log.e("post", "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                                                    Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            }) {

                                                @Override
                                                protected Map<String, String> getParams() {
                                                    Map<String, String> params = new HashMap<String, String>();
                                                    String userid = PrefUtils.getValue(SentActivity.this, "app_pref", "userid");


                                                    params.put("userid", userid);
                                                    params.put("category", inboxmessagelist.get(index).getCategory());
                                                    params.put("userto", inboxmessagelist.get(index).getTo());
                                                    params.put("productid", inboxmessagelist.get(index).getProductid());
                                                    params.put("message", etfeedback.getText().toString());

                                                    Log.e("post", "Params: " + params.toString());

                                                    return params;
                                                }

                                                ;
                                            };


                                            // disabling retry policy so that it won't make
                                            // multiple http calls
                                            int socketTimeout = 0;
                                            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

                                            strReq.setRetryPolicy(policy);

                                            RequestQueue requestQueue = Volley.newRequestQueue(SentActivity.this);
                                            requestQueue.add(strReq);


                                        } else {
                                            etfeedback.setError("Message can't be empty.");
                                        }

                                    }
                                })
                                .show();


                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

            }


        })
                .show();




    }

    @Override
    public void onLongClick(int index) {

    }
}
