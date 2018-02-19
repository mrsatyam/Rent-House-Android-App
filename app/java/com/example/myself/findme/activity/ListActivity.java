package com.example.myself.findme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.myself.findme.R;
import com.example.myself.findme.adapter.HomeRecyclerAdapterTrend;
import com.example.myself.findme.adapter.ListAdapter;
import com.example.myself.findme.model.ProductListModel;
import com.example.myself.findme.model.ProductModel;
import com.example.myself.findme.network.Background_Network;
import com.example.myself.findme.network.Background_Network_Class;
import com.example.myself.findme.util.CallBackInterface;
import com.example.myself.findme.util.ConnectionDetector;
import com.example.myself.findme.util.EndPoints;
import com.example.myself.findme.util.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements CallBackInterface, ListAdapter.ClickListener {

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    Background_Network_Class background_network;
    List<ProductListModel> hostellist;

    String table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        table=getIntent().getExtras().getString("table");

        if(table.contains("hostel"))
        {
getSupportActionBar().setTitle("Hostels & PGs");
        }
        else if(table.contains("house"))
        {
            getSupportActionBar().setTitle("Houses");
        }
        else if(table.contains("project"))
        {
            getSupportActionBar().setTitle("Housing Project");
        }
        else if(table.contains("rent"))
        {
            getSupportActionBar().setTitle("Rent");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // if connection available
        if (new ConnectionDetector(this).isConnectingToInternet()) {
            background_network = new Background_Network_Class(this);
            background_network.execute(EndPoints.RETRIEVE_MAIN + "?city="+ PrefUtils.getValue(ListActivity.this, "app_pref", "cityname")+"&table="+table);
        } else {
            Toast.makeText(this, "network not available", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void parsing(String s) throws JSONException {


        JSONObject jsonObject = new JSONObject(s);

        int success = jsonObject.getInt("success");

        if (success == 1) {

            JSONArray arrayhotel = jsonObject.getJSONArray(table);

             hostellist = new ArrayList<>();

            for (int i = 0; i < arrayhotel.length(); i++) {
                JSONObject jsonObject1 = arrayhotel.getJSONObject(i);

                String id = jsonObject1.getString("id");
                String title = jsonObject1.getString("title");
                String area = jsonObject1.getString("area");
                String address = jsonObject1.getString("address");
                String city = jsonObject1.getString("city");
                String createdat = jsonObject1.getString("created_at");
                String image = jsonObject1.getString("main_image");
                String price = jsonObject1.getString("price");
                String short_tag = jsonObject1.getString("short_tag");
                String contact_no = jsonObject1.getString("contact_no");
                String description = jsonObject1.getString("description");
                String userid = jsonObject1.getString("user_id");


                ProductListModel product = new ProductListModel(image, id, title, area, address, city, createdat, price, short_tag, description, contact_no,userid);

                hostellist.add(product);
            }

            llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
           ListAdapter ca = new ListAdapter(this,this,hostellist);
            recyclerView.setLayoutManager(llm);
           recyclerView.setAdapter(ca);

background_network.progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(int index) {

        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("id",hostellist.get(index).getId());
        intent.putExtra("table",table);
        intent.putExtra("userid",hostellist.get(index).getUserid());
        startActivity(intent);

    }

    @Override
    public void onLongClick(int index) {

    }
}
