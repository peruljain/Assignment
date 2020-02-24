package com.example.assignment;

import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";
    private  ArrayList<DataModel> dataModelArrayList;
    private RvAdapter rvAdapter;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout parentShimmerLayout;
    private SwipeRefreshLayout swipeContainer;
    private  ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     recyclerView = findViewById(R.id.recycler);
     parentShimmerLayout = findViewById(R.id.shimmer_view_container);
     swipeContainer = findViewById(R.id.swipe_container);
     fetchingJSON();
     swipe_refresh();
     actionBar = getSupportActionBar();
     actionBar.hide();


    }


    // fetching the data
    private void fetchingJSON() {

        parentShimmerLayout.startShimmerAnimation();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {


                            JSONObject obj = new JSONObject(response);
                            String title = obj.getString("title");
                            actionBar.setTitle(title);
                            actionBar.show();

                                JSONArray dataArray  = obj.getJSONArray("rows");
                            dataModelArrayList = new ArrayList<>();

                                for (int i = 0; i < dataArray.length(); i++) {

                                    DataModel playerModel = new DataModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setTitle(dataobj.getString("title"));
                                    playerModel.setDescription(dataobj.getString("description"));
                                    playerModel.setImgURL(dataobj.getString("imageHref"));

                                    dataModelArrayList.add(playerModel);

                                }


                                setupRecycler();
                                parentShimmerLayout.stopShimmerAnimation();
                                parentShimmerLayout.setVisibility(View.GONE);
                                swipeContainer.setRefreshing(false);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private void setupRecycler(){

        rvAdapter = new RvAdapter(this,dataModelArrayList);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    private void swipe_refresh(){
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actionBar.hide();
                fetchingJSON();
                rvAdapter.notifyDataSetChanged();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onResume() {
        super.onResume();
        parentShimmerLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        parentShimmerLayout.stopShimmerAnimation();
        super.onPause();
    }



}