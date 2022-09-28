package com.example.groceryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groceryshop.Adapter.CartAdapter;
import com.example.groceryshop.Adapter.CategoriesClickAdapter;
import com.example.groceryshop.Adapter.CategorieslistAdapter;
import com.example.groceryshop.Adapter.FilterCategoriesAdapter;
import com.example.groceryshop.Adapter.TopCategoriesAdapter;
import com.example.groceryshop.Modal.Cartlistdata;
import com.example.groceryshop.Modal.Categorieslistdata;
import com.example.groceryshop.Modal.Filtercategoriesdata;
import com.example.groceryshop.Modal.TopProductdata;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryItemActivity extends AppCompatActivity {
     RecyclerView recyclerView,recyclerView1;
    CategoriesClickAdapter adapter;
    String url="https://nirajganeshphp.000webhostapp.com/categoriesdata.php";
    StringRequest request;
    ArrayList<Categorieslistdata> topProductdata=new ArrayList<>();
    ArrayList<Filtercategoriesdata> topProductdata1=new ArrayList<>();
    Context context=CategoryItemActivity.this;
    int total=0;
    TextView pricetext;
    FilterCategoriesAdapter topCategoriesAdapter;
    String url1="https://nirajganeshphp.000webhostapp.com/productdata.php";
    StringRequest request1;
    String categoriesname;
    Toolbar toolbar;
    ShimmerFrameLayout shimmerFrameLayout,shimmerFrameLayout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);
        categoriesname=getIntent().getStringExtra("name").toString();
        recyclerView=findViewById(R.id.recyclerviewid);
        shimmerFrameLayout=findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();

        shimmerFrameLayout1=findViewById(R.id.shimmerLayout1);
        shimmerFrameLayout1.startShimmer();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView1=findViewById(R.id.recyclerviewid1);
        StaggeredGridLayoutManager linearLayoutManager1=new StaggeredGridLayoutManager(3,RecyclerView.VERTICAL);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                {
                    //  progressBar.setVisibility(View.INVISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("store");
                        for(int i=0;i<jsonArray.length();i++)
                        {

                            JSONObject object=jsonArray.getJSONObject(i);
                            Categorieslistdata jsondata=new Categorieslistdata();
                            jsondata.setAuthor(object.getString("categoriesname"));
                            jsondata.setDownload_url(object.getString("categoriesimage"));
                            jsondata.setId(object.getString("categoriesid"));
                            topProductdata.add(jsondata);
                            adapter=new CategoriesClickAdapter(context,topProductdata,categoriesname);
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



        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);

        request1=new StringRequest(url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                {
                    //  progressBar.setVisibility(View.INVISIBLE);
                    shimmerFrameLayout1.stopShimmer();
                    shimmerFrameLayout1.setVisibility(View.INVISIBLE);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("store");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object=jsonArray.getJSONObject(i);
                            Filtercategoriesdata productdata=new Filtercategoriesdata();
                            if(categoriesname.equals(object.getString("categoriestype"))) {
                                productdata.setProductname(object.getString("productname"));
                                productdata.setProductimage(object.getString("productimage"));
                                productdata.setProductprice(object.getString("productprice"));
                                productdata.setCategoriestype(object.getString("categoriestype"));
                                productdata.setProductid(object.getString("productid"));
                                topProductdata1.add(productdata);
                                Toast.makeText(context, productdata.getProductname().toString(), Toast.LENGTH_LONG).show();
                                topCategoriesAdapter = new FilterCategoriesAdapter(context, topProductdata1, categoriesname);
                                recyclerView1.setAdapter(topCategoriesAdapter);
                                //      progressBar.setVisibility(View.INVISIBLE);
                            }

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
        queue1.add(request1);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.searchid)
        {
            Intent intent=new Intent(CategoryItemActivity.this,SearchActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.wishlist)
        {
            Intent intent=new Intent( CategoryItemActivity.this,WishlistActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();
    }
}