package com.example.groceryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groceryshop.Adapter.OrderAdapter;
import com.example.groceryshop.Adapter.TopCategoriesAdapter;
import com.example.groceryshop.Modal.TopProductdata;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {
     OrderAdapter adapter;
     RecyclerView recyclerView;
     ArrayList<TopProductdata> topProductdata=new ArrayList<>();
    String url="https://nirajganeshphp.000webhostapp.com/productdata.php";
    StringRequest request;
    Toolbar toolbar;
    Context context=MyOrderActivity.this;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();
        recyclerView=findViewById(R.id.recyclerviewid2);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                {
                     shimmerFrameLayout.stopShimmer();
                     shimmerFrameLayout.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("store");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object=jsonArray.getJSONObject(i);
                            TopProductdata productdata=new TopProductdata();
                            productdata.setProductname(object.getString("productname"));
                            productdata.setProductimage(object.getString("productimage"));
                            productdata.setProductprice(object.getString("productprice"));
                            //     productdata.setProductid(object.getString("proudctid"));
                            topProductdata.add(productdata);
                            adapter=new OrderAdapter(context,topProductdata);
                            recyclerView.setAdapter(adapter);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue1= Volley.newRequestQueue(this);
        queue1.add(request);





    }
}