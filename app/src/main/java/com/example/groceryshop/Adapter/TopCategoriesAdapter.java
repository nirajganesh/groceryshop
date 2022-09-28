package com.example.groceryshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryshop.Modal.TopProductdata;
import com.example.groceryshop.ProductviewActivity;
import com.example.groceryshop.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class TopCategoriesAdapter extends RecyclerView.Adapter<TopCategoriesAdapter.ViewHolder> {
    Context context;
    ArrayList<TopProductdata> topProductdata;
    EasyDB easyDB;
    Integer ID;
    String totalvalue;


    public TopCategoriesAdapter(Context context, ArrayList<TopProductdata> topProductdata) {
        this.context = context;
        this.topProductdata = topProductdata;
        easyDB = EasyDB.init(context, "CART") // TEST is the name of the DATABASE
                .setTableName("DEMO")  // You can ignore this line if you want
                .addColumn(new Column("Itemid",new String[]{"text","unique"})) // Contrains like "text", "unique", "not null" are not case sensitive
                .addColumn(new Column("Itemname",new String[]{"text"}))
                .addColumn(new Column("Itemurl",new String[]{"text"}))
                .addColumn(new Column("Itemquantity",new String[]{"text"}))
                .addColumn(new Column("Itemprice",new String[]{"text"}))
                .doneTableColumn();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.topcategories_listview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(topProductdata.get(position).getProductimage()).into(holder.imageView);
        holder.name.setText(topProductdata.get(position).getProductname());
        holder.price.setText(topProductdata.get(position).getProductprice());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean done = easyDB.addData("Itemid", topProductdata.get(position).getProductid())
                        .addData("Itemname", topProductdata.get(position).getProductname())
                        .addData("Itemurl", topProductdata.get(position).getProductimage())
                        .addData("Itemprice", topProductdata.get(position).getProductprice())
                        .addData("Itemquantity",1)
                        .doneDataAdding();


                Cursor res=easyDB.getAllData();
                while (res.moveToNext())
                {
                    String item_id = res.getString(1);
                    if(item_id.equals(topProductdata.get(position).getProductid()))
                    {
                        ID=res.getInt(0);
                        totalvalue=res.getString(4);
                    }
                }

                if(done)
                {

                    Toasty.success(context, "Added Cart..", Toast.LENGTH_SHORT, true).show();
                }
                if(!done)
                {
                    Integer total= Integer.valueOf(totalvalue)+1;
                    String change=String.valueOf(total);
                    boolean updated = easyDB.updateData(4,change)
                            .rowID(ID);
                    Snackbar snackbar = Snackbar
                            .make(holder.relativeLayout, "Updated your items.....", Snackbar.LENGTH_LONG)
                            .setAction("", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(holder.relativeLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });
                    snackbar.show();
                 //   Toasty.success(context, "Updated Cart item..", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductviewActivity.class);
                intent.putExtra("image",topProductdata.get(position).getProductimage().toString());
                intent.putExtra("name",topProductdata.get(position).getProductname().toString());
                intent.putExtra("price",topProductdata.get(position).getProductprice().toString());
                intent.putExtra("productid",topProductdata.get(position).getProductid());
                intent.putExtra("rowid",topProductdata.get(position).getRowid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return topProductdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView imageView;
         TextView name,description,price;
         ImageButton wishlist;
         Button add;
         CardView cardView;
         RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageid);
            name=itemView.findViewById(R.id.nameid);
            description=itemView.findViewById(R.id.descriptionid);
            price=itemView.findViewById(R.id.priceid);
            wishlist=itemView.findViewById(R.id.wishlistid);
            add=itemView.findViewById(R.id.addid);
            cardView=itemView.findViewById(R.id.cardid);
            relativeLayout=itemView.findViewById(R.id.relativelayoutid);
        }
    }
}
