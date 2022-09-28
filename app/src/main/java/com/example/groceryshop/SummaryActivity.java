package com.example.groceryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groceryshop.Adapter.CartAdapter;
import com.example.groceryshop.Adapter.TopCategoriesAdapter;
import com.example.groceryshop.Modal.Cartlistdata;
import com.example.groceryshop.Modal.Categorieslistdata;
import com.example.groceryshop.Modal.TopProductdata;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import p32929.androideasysql_library.EasyDB;

public class SummaryActivity extends AppCompatActivity {

    TopCategoriesAdapter topCategoriesAdapter;
    Context context=SummaryActivity.this;
    ArrayList<Categorieslistdata> jsondataArrayList=new ArrayList<>();
    ArrayList<TopProductdata> topProductdata=new ArrayList<>();
    String url1="https://nirajganeshphp.000webhostapp.com/categoriesdata.php";
    String url="https://nirajganeshphp.000webhostapp.com/productdata.php";
    StringRequest request;
    StringRequest request1;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    Toolbar toolbar;
    Button continuebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        recyclerView=findViewById(R.id.recyclerviewid1);

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar=findViewById(R.id.toolbar);
        continuebtn=findViewById(R.id.continueid2);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SummaryActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });


        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SummaryActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        request1=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null)
                {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    //   progressBar1.setVisibility(View.INVISIBLE);
                 //   topproducttext.setVisibility(View.VISIBLE);
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
                            productdata.setProductid(object.getString("productid"));
                            topProductdata.add(productdata);
                            //    Toast.makeText(context,productdata.getProductprice().toString(),Toast.LENGTH_LONG).show();
                            topCategoriesAdapter=new TopCategoriesAdapter(context,topProductdata);
                            recyclerView.setAdapter(topCategoriesAdapter);
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
        queue1.add(request1);
    }

}