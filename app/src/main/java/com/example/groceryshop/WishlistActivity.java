package com.example.groceryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groceryshop.Adapter.TopCategoriesAdapter;
import com.example.groceryshop.Adapter.WishlistAdapter;
import com.example.groceryshop.Modal.TopProductdata;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {
    String url="https://nirajganeshphp.000webhostapp.com/productdata.php";
    StringRequest request;
    RecyclerView recyclerView;
    WishlistAdapter adapter;
    ArrayList<TopProductdata> topProductdata=new ArrayList<>();
    Context context=WishlistActivity.this;
    Toolbar toolbar;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
         toolbar=findViewById(R.id.toolbar);

         shimmerFrameLayout=findViewById(R.id.shimmerLayout);
         shimmerFrameLayout.startShimmer();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView=findViewById(R.id.recyclerviewid);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                {
                     shimmerFrameLayout.startShimmer();
                     shimmerFrameLayout.setVisibility(View.INVISIBLE);
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
                            //    Toast.makeText(context,productdata.getProductprice().toString(),Toast.LENGTH_LONG).show();
                            adapter=new WishlistAdapter(context,topProductdata);
                            recyclerView.setAdapter(adapter);
                            //      progressBar.setVisibility(View.INVISIBLE);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wishlist_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.bagid)
        {
            Intent intent=new Intent(WishlistActivity.this, CardActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}