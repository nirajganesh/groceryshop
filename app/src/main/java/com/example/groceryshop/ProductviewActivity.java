package com.example.groceryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import es.dmoral.toasty.Toasty;
import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class ProductviewActivity extends AppCompatActivity {
    ImageView imageView;
    TextView name,price,quantity;
    ImageButton add,remove;
    String imageintent,nameintent,priceintent,productidintent,rowidintent;
    Toolbar toolbar;
    EasyDB easyDB;
    Button addcart;
    String totalvalue;
    Integer ID;
    Integer count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);

        imageView=findViewById(R.id.imageid);
        name=findViewById(R.id.nameid);
        price=findViewById(R.id.priceid);
        toolbar=findViewById(R.id.toolbar);
        add=findViewById(R.id.plusid);
        remove=findViewById(R.id.minusid);
        quantity=findViewById(R.id.textquantityid);
        addcart=findViewById(R.id.addcartid);
        imageintent=getIntent().getStringExtra("image").toString();
        nameintent=getIntent().getStringExtra("name").toString();
        priceintent=getIntent().getStringExtra("price").toString();
        productidintent=getIntent().getStringExtra("productid");
        rowidintent=getIntent().getStringExtra("rowid");

        easyDB = EasyDB.init(ProductviewActivity.this, "CART") // TEST is the name of the DATABASE
                .setTableName("DEMO")  // You can ignore this line if you want
                .addColumn(new Column("Itemid",new String[]{"text","unique"})) // Contrains like "text", "unique", "not null" are not case sensitive
                .addColumn(new Column("Itemname",new String[]{"text","unique"}))
                .addColumn(new Column("Itemurl",new String[]{"text"}))
                .addColumn(new Column("Itemquantity",new String[]{"text"}))
                .addColumn(new Column("Itemprice",new String[]{"text"}))
                .doneTableColumn();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 count=count+1;
                 String stringcount=String.valueOf(count);
                 quantity.setText(stringcount);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<2)
                {
                    Toasty.error(ProductviewActivity.this, "Quanity value not zero...", Toast.LENGTH_SHORT, true).show();
                }else
                {
                    count=count-1;
                    String stringcount=String.valueOf(count);
                    quantity.setText(stringcount);
                }
            }
        });
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean done = easyDB.addData("Itemid", productidintent)
                        .addData("Itemname", nameintent)
                        .addData("Itemurl", imageintent)
                        .addData("Itemprice", priceintent)
                        .addData("Itemquantity",count)
                        .doneDataAdding();

                Cursor res=easyDB.getAllData();
                while (res.moveToNext())
                {
                    String item_id = res.getString(1);
                    if(item_id.equals(productidintent))
                    {
                        ID=res.getInt(0);
                        totalvalue=res.getString(4);
                    }
                }

                if(done)
                {
                    Toasty.success(ProductviewActivity.this, "Added Cart..", Toast.LENGTH_SHORT, true).show();
                }
                if(!done)
                {
                    Integer total= Integer.valueOf(totalvalue)+count;
                    String change=String.valueOf(total);
                    boolean updated = easyDB.updateData(4,change)
                            .rowID(ID);
                    Toasty.success(ProductviewActivity.this, "Updated Cart item..", Toast.LENGTH_SHORT, true).show();
                }

                Cursor res1 = easyDB.getAllData();
                while (res1.moveToNext()) {
                    int anIntegerVariable = res1.getInt(0);
                    String item_id = res1.getString(1);
                    String item_author=res1.getString(2);
                    String item_url=res1.getString(3);
                }
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Glide.with(ProductviewActivity.this).load(imageintent).into(imageView);
        name.setText(nameintent);
        price.setText(priceintent);
    }
}