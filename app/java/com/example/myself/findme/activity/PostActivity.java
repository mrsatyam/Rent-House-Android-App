package com.example.myself.findme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.example.myself.findme.util.EndPoints;
import com.example.myself.findme.util.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    Spinner spCategory, spCity;
    EditText ettitle, etarea, etaddress, etshorttags, etdescription, etprice, etcontact;
    Button btpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        spCategory = (Spinner) findViewById(R.id.Category);
        spCity = (Spinner) findViewById(R.id.City);
        ettitle = (EditText) findViewById(R.id.Title);
        etarea = (EditText) findViewById(R.id.Area);
        etaddress = (EditText) findViewById(R.id.Address);
        etshorttags = (EditText) findViewById(R.id.ShortTags);
        etdescription = (EditText) findViewById(R.id.Description);
        etprice = (EditText) findViewById(R.id.Price);
        etcontact = (EditText) findViewById(R.id.Contact);

        btpost = (Button) findViewById(R.id.btn_post);


        btpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        EndPoints.POST_MESSAGE, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("post", "response: " + response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            // check for error
                            if (obj.getInt("success") == 1) {

                                Toast.makeText(PostActivity.this,"successfully post ads",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PostActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
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
                        String userid = PrefUtils.getValue(PostActivity.this, "app_pref", "userid");

                        int selected = spCategory.getSelectedItemPosition();

                        String category = null;

                        if (selected == 0) {
                            category = "hostelpg";
                        } else if (selected == 1) {
                            category = "house";
                        } else if (selected == 2) {
                            category = "project";
                        } else if (selected == 3) {
                            category = "rentlease";
                        }

                        params.put("userid", userid);
                        params.put("category", category);
                        params.put("title", ettitle.getText().toString());
                        params.put("city", spCity.getSelectedItem().toString());
                        params.put("area", etarea.getText().toString());
                        params.put("address", etaddress.getText().toString());
                        params.put("shorttags", etshorttags.getText().toString());
                        params.put("description", etdescription.getText().toString());
                        params.put("price", etprice.getText().toString());
                        params.put("contact", etcontact.getText().toString());
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

                RequestQueue requestQueue = Volley.newRequestQueue(PostActivity.this);
                requestQueue.add(strReq);
            }
        });


    }

}
