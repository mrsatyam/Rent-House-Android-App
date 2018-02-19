package com.example.myself.findme.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.myself.findme.R;
import com.example.myself.findme.activity.ListActivity;
import com.example.myself.findme.adapter.HomeCategoryAdapter;
import com.example.myself.findme.adapter.HomeRecyclerAdapterTrend;
import com.example.myself.findme.model.ProductModel;
import com.example.myself.findme.model.ProductCategory;
import com.example.myself.findme.network.Background_Network;
import com.example.myself.findme.util.CallBackInterface;
import com.example.myself.findme.util.ConnectionDetector;
import com.example.myself.findme.util.EndPoints;
import com.example.myself.findme.util.PrefUtils;
import com.karumi.dividers.DividerBuilder;
import com.karumi.dividers.DividerItemDecoration;
import com.karumi.dividers.Layer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by GB on 11/13/2015.
 */


public class HomeFragment extends android.support.v4.app.Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, HomeCategoryAdapter.ClickListener, CallBackInterface {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    List<List<ProductModel>> lists;
    private SliderLayout mDemoSlider;

    private static final String ARG_SECTION_NUMBER = "section_number";

    RecyclerView home_recyclerview_trend, home_recyclerview_category;
    LinearLayoutManager llm, llc;
    StaggeredGridLayoutManager sglm;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    Background_Network background_network;


    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.home_slider);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Homes For All", R.drawable.homes_for_all);
        file_maps.put("Hostels And PG", R.drawable.hostel_pg_category);
        file_maps.put("House", R.drawable.house_category);
        file_maps.put("Projects", R.drawable.housing_project);



        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);


        // trend recycler view
        home_recyclerview_trend = (RecyclerView) rootView.findViewById(R.id.home_recycler_view_trend);


        // category recycler view
        home_recyclerview_category = (RecyclerView) rootView.findViewById(R.id.home_reycler_view_category);
        sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        llc = new LinearLayoutManager(getActivity());
        llc.setOrientation(LinearLayoutManager.VERTICAL);
        HomeCategoryAdapter hcat = new HomeCategoryAdapter(getActivity(), this, getItems());
        home_recyclerview_category.setLayoutManager(sglm);
        Drawable exampleDrawable = getResources().getDrawable(R.drawable.category_light_divider);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(new Layer(DividerBuilder.get().with(exampleDrawable).build()));
        home_recyclerview_category.addItemDecoration(itemDecoration);


        home_recyclerview_category.setAdapter(hcat);


        // if connection available
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            background_network = new Background_Network(HomeFragment.this);
            background_network.execute(EndPoints.TOP_TREND+"?city="+ PrefUtils.getValue(getActivity(), "app_pref", "cityname"));
        } else {
            Toast.makeText(getActivity(), "network not available", Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }

    private List<ProductCategory> getItems() {
        List<ProductCategory> items = new ArrayList<>();

        items.add(new ProductCategory("Hostel and PG", "", 93, R.drawable.hostel_pg_category));
        items.add(new ProductCategory("House", "", 89, R.drawable.house_category));
        items.add(new ProductCategory("Project", "", 89, R.drawable.housing_project));
        items.add(new ProductCategory("Rent and Lease", "", 89, R.drawable.angry_men));


        return items;
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCategoryClick(int index) {

        switch (index) {
            case 0:

             Intent intent = new Intent(getActivity(),ListActivity.class);
                intent.putExtra("table","hostelpg");
                startActivity(intent);


                break;

            case 1:

                Intent intent2 = new Intent(getActivity(),ListActivity.class);
                intent2.putExtra("table","house");
                startActivity(intent2);

                break;

            case 2:
                Intent intent3 = new Intent(getActivity(),ListActivity.class);
                intent3.putExtra("table","project");
                startActivity(intent3);

                break;
            case 3:
                Intent intent4 = new Intent(getActivity(),ListActivity.class);
                intent4.putExtra("table","rentlease");
                startActivity(intent4);

                break;
        }
    }

    @Override
    public void onCategoryLongClick(int index) {

    }

    @Override
    public void parsing(String s) throws JSONException {


        JSONObject jsonObject = new JSONObject(s);

        int success = jsonObject.getInt("success");

        if(success==1) {

            JSONArray arrayhotel = jsonObject.getJSONArray("hostelpg");

            List<ProductModel> hostellist = new ArrayList<>();

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


                ProductModel product = new ProductModel(image, id, title, area, address, city, createdat, price);

                hostellist.add(product);
            }

            JSONArray arrayproject = jsonObject.getJSONArray("project");

            List<ProductModel> projectlist = new ArrayList<>();

            for (int i = 0; i < arrayproject.length(); i++) {
                JSONObject jsonObject1 = arrayproject.getJSONObject(i);

                String id = jsonObject1.getString("id");
                String title = jsonObject1.getString("title");
                String area = jsonObject1.getString("area");
                String address = jsonObject1.getString("address");
                String city = jsonObject1.getString("city");
                String createdat = jsonObject1.getString("created_at");
                String image = jsonObject1.getString("main_image");
                String price = jsonObject1.getString("price");


                ProductModel product = new ProductModel(image, id, title, area, address, city, createdat, price);

                projectlist.add(product);
            }

            JSONArray arrayhouse = jsonObject.getJSONArray("house");

            List<ProductModel> housellist = new ArrayList<>();

            for (int i = 0; i < arrayhouse.length(); i++) {
                JSONObject jsonObject1 = arrayhouse.getJSONObject(i);

                String id = jsonObject1.getString("id");
                String title = jsonObject1.getString("title");
                String area = jsonObject1.getString("area");
                String address = jsonObject1.getString("address");
                String city = jsonObject1.getString("city");
                String createdat = jsonObject1.getString("created_at");
                String image = jsonObject1.getString("main_image");
                String price = jsonObject1.getString("price");


                ProductModel product = new ProductModel(image, id, title, area, address, city, createdat, price);

                housellist.add(product);
            }

            JSONArray arrayrent = jsonObject.getJSONArray("rentlease");

            List<ProductModel> rentlist = new ArrayList<>();

            for (int i = 0; i < arrayrent.length(); i++) {
                JSONObject jsonObject1 = arrayrent.getJSONObject(i);

                String id = jsonObject1.getString("id");
                String title = jsonObject1.getString("title");
                String area = jsonObject1.getString("area");
                String address = jsonObject1.getString("address");
                String city = jsonObject1.getString("city");
                String createdat = jsonObject1.getString("created_at");
                String image = jsonObject1.getString("main_image");
                String price = jsonObject1.getString("price");

                ProductModel product = new ProductModel(image, id, title, area, address, city, createdat, price);

                rentlist.add(product);
            }

            lists = new ArrayList<List<ProductModel>>();
            lists.add(hostellist);
            lists.add(housellist);
            lists.add(projectlist);
            lists.add(rentlist);

            // adapter and set


            llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            HomeRecyclerAdapterTrend ca = new HomeRecyclerAdapterTrend(getActivity(), lists);
            home_recyclerview_trend.setLayoutManager(llm);
            home_recyclerview_trend.setAdapter(ca);


        }
        background_network.progressDialog.dismiss();;
    }
}



