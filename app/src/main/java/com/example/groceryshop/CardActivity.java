package com.example.groceryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.groceryshop.Modal.TopProductdata;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class CardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CartAdapter adapter;
    String url="https://nirajganeshphp.000webhostapp.com/productdata.php";
    StringRequest request;
    ArrayList<Cartlistdata> topProductdata=new ArrayList<>();
    Context context=CardActivity.this;
    int total=0;
    TextView pricetext;
    Toolbar toolbar;
    ShimmerFrameLayout shimmerFrameLayout;
    EasyDB easyDB;
    int peritemcount;
    Button checkbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        pricetext=findViewById(R.id.priceid);
        recyclerView=findViewById(R.id.recyclerviewid);

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar=findViewById(R.id.toolbar);
        checkbtn=findViewById(R.id.addid2);
        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CardActivity.this,SummaryActivity.class);
                startActivity(intent);
            }
        });


        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CardActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        easyDB = EasyDB.init(context, "CART") // TEST is the name of the DATABASE
                .setTableName("DEMO")  // You can ignore this line if you want
                .addColumn(new Column("Itemid",new String[]{"text","unique"})) // Contrains like "text", "unique", "not null" are not case sensitive
                .addColumn(new Column("Itemname",new String[]{"text"}))
                .addColumn(new Column("Itemurl",new String[]{"text"}))
                .addColumn(new Column("Itemprice",new String[]{"text"}))
                .addColumn(new Column("Itemquantity",new String[]{"text"}))
                .doneTableColumn();



        Cursor res = easyDB.getAllData();
        while (res.moveToNext()) {
            int id = res.getInt(0);
            String item_id  = res.getString(1);
            String item_name=res.getString(2);
            String item_url=res.getString(3);
            String item_quantity=res.getString(4);
            String item_price=res.getString(5);
            Cartlistdata cartdata=new Cartlistdata();
          //  cartdata.setProductid(item_id);
            cartdata.setProductimage(item_url);
            cartdata.setProductname(item_name);
            cartdata.setProductprice(item_price);
            cartdata.setProductquantity(item_quantity);
            cartdata.setRowid(id);
            peritemcount=Integer.parseInt(item_quantity)*Integer.parseInt(item_price);
            total=peritemcount+total;
            topProductdata.add(cartdata);


        }
      //  totalcount=String.valueOf(total);
      //  itemtotal.setText(totalcount);
        pricetext.setText(String.valueOf(total));
     //   progressBar.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        adapter=new CartAdapter(context,topProductdata,easyDB,pricetext);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.favoritoresid)
        {
            Intent intent=new Intent( CardActivity.this,WishlistActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(CardActivity.this,MainActivity.class);
        startActivity(intent);
    }
}