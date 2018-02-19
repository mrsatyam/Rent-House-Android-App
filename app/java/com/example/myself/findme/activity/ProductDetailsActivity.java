package com.example.myself.findme.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.myself.findme.adapter.ListAdapter;
import com.example.myself.findme.model.ProductListModel;
import com.example.myself.findme.network.Background_Network_Class;
import com.example.myself.findme.util.CallBackInterface;
import com.example.myself.findme.util.ConnectionDetector;
import com.example.myself.findme.util.EndPoints;
import com.example.myself.findme.util.PrefUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProductDetailsActivity extends AppCompatActivity implements CallBackInterface {

    Background_Network_Class background_network;

    TextView tvtitle,tvvenu,tvdescription,tvprice,tvtags,tvaddress;
    ImageView image;
    String number;
    String id;
    String table;

    Button btncall,btnmsg,btnmessagepost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

      id = getIntent().getExtras().getString("id");
     table = getIntent().getExtras().getString("table");

        tvtitle=(TextView)findViewById(R.id.Title);
        tvvenu=(TextView)findViewById(R.id.Venue);
        tvdescription=(TextView)findViewById(R.id.Description);
        tvprice=(TextView)findViewById(R.id.Price);
        tvtags=(TextView)findViewById(R.id.Tags);
        image=(ImageView)findViewById(R.id.image);
        tvaddress=(TextView)findViewById(R.id.Address);
        btncall=(Button)findViewById(R.id.btnCall);
        btnmsg=(Button)findViewById(R.id.btnMsg);
        btnmessagepost=(Button)findViewById(R.id.btnMessage);

        // if connection available
        if (new ConnectionDetector(this).isConnectingToInternet()) {
            background_network = new Background_Network_Class(this);
            background_network.execute(EndPoints.RETRIEVE_DETAILS+ "?city="+PrefUtils.getValue(ProductDetailsActivity.this, "app_pref", "cityname")  +"&table="+table+"&id="+id);
        } else {
            Toast.makeText(this, "network not available", Toast.LENGTH_SHORT).show();
        }

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(ProductDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
              startActivity(intent);
            }
        });

        btnmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("address",number);
                sendIntent.putExtra("sms_body","");
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

        btnmessagepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = PrefUtils.getValue(ProductDetailsActivity.this, "app_pref", "userid");

               if(userid!=null) {


                    new MaterialDialog.Builder(ProductDetailsActivity.this)
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

                                                        Toast.makeText(ProductDetailsActivity.this, "successfully sent message", Toast.LENGTH_SHORT).show();

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
                                                String userid = PrefUtils.getValue(ProductDetailsActivity.this, "app_pref", "userid");


                                                params.put("userid", userid);
                                                params.put("category", table);
                                                params.put("userto", getIntent().getExtras().getString("userid"));
                                                params.put("productid", id);
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

                                        RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailsActivity.this);
                                        requestQueue.add(strReq);


                                    } else {
                                        etfeedback.setError("Message can't be empty.");
                                    }

                                }
                            })
                            .show();

                }
                else
               {
                   Toast.makeText(ProductDetailsActivity.this,"Login to proceed",Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    @Override
    public void parsing(String s) throws JSONException {

        JSONObject jsonObject = new JSONObject(s);

        int success = jsonObject.getInt("success");

        if (success == 1) {

            JSONArray arrayhotel = jsonObject.getJSONArray(table);



            for (int i = 0; i < arrayhotel.length(); i++) {
                JSONObject jsonObject1 = arrayhotel.getJSONObject(i);

                String id = jsonObject1.getString("id");
                String title = jsonObject1.getString("title");
                String area = jsonObject1.getString("area");
                String address = jsonObject1.getString("address");
                String city = jsonObject1.getString("city");
                String createdat = jsonObject1.getString("created_at");
                String simage = jsonObject1.getString("main_image");
                String price = jsonObject1.getString("price");
                try {
                    String short_tag = jsonObject1.getString("short_tag");
                    String contact_no = jsonObject1.getString("contact_no");
                    String description = jsonObject1.getString("description");
                    tvtags.setText(short_tag);
                    tvdescription.setText(description);
                    number=contact_no;
                }catch(Exception e)
                {

                }

                if(simage!=null) {
                    Picasso.with(ProductDetailsActivity.this)
                            .load(EndPoints.baseaddress + "/" +simage)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(image)
                    ;
                }
                else
                {
                 image.setImageResource(R.drawable.placeholder);
                }

                tvtitle.setText(title);

                tvvenu.setText(area);
                tvaddress.setText(address+"\n"+city);
                tvprice.setText("Rs."+price+"\n\n"+number);


            }




        }

        background_network.progressDialog.dismiss();;



    }
}
