package com.example.groceryshop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryshop.CardActivity;
import com.example.groceryshop.Modal.Cartlistdata;
import com.example.groceryshop.Modal.TopProductdata;
import com.example.groceryshop.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import p32929.androideasysql_library.EasyDB;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    ArrayList<Cartlistdata> topProductdata;
    Animation animation;
    EasyDB easyDB;
    String total;
    AlertDialog.Builder builder;
    TextView pricetext;
    Integer totalcount;
    String pricestring,pricecurrent;


    public CartAdapter(Context context, ArrayList<Cartlistdata> topProductdata,EasyDB easyDB,TextView pricetext) {
        this.context = context;
        this.topProductdata = topProductdata;
        animation= AnimationUtils.loadAnimation(context,R.anim.right_animation);
         this.easyDB=easyDB;
        builder = new AlertDialog.Builder(context);
        this.pricetext=pricetext;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_listview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardView.setAnimation(animation);
        Glide.with(context).load(topProductdata.get(position).getProductimage()).into(holder.imageView);
        holder.name.setText(topProductdata.get(position).getProductname());
        holder.price.setText(topProductdata.get(position).getProductprice());
        holder.quantity.setText(topProductdata.get(position).getProductquantity());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are your conform delete..") .setTitle("same pinch...");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to delete the item ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean deleted = easyDB.deleteRow(2, topProductdata.get(position).getProductname());
                                Toasty.success(context, "Delete Item...", Toast.LENGTH_SHORT, true).show();
                                Intent intent=new Intent(context, CardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("GroceryShop");
                alert.show();

            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 pricestring=pricetext.getText().toString();
                 pricecurrent=holder.price.getText().toString();
                Toasty.info(context, "Quantity added.....", Toast.LENGTH_SHORT, true).show();
                Cursor res = easyDB.getOneRowData(topProductdata.get(position).getRowid());
                String tota;
                if (res != null) {
                    res.moveToFirst(); // Because here's only one row data
                     total = res.getString(5); // Column 0 is the ID column
                }
                totalcount= Integer.valueOf(pricestring)+Integer.valueOf(pricecurrent);
                Integer increase=Integer.parseInt(total)+1;
                String totalvalue=String.valueOf(increase);
                boolean updated = easyDB.updateData(5,totalvalue)
                        .rowID(topProductdata.get(position).getRowid());
                holder.quantity.setText(totalvalue);
                String contotalcount=String.valueOf(totalcount);
                pricetext.setText("");
                pricetext.setText(contotalcount);
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pricestring=pricetext.getText().toString();
                pricecurrent=holder.price.getText().toString();
                Cursor res = easyDB.getOneRowData(topProductdata.get(position).getRowid());
                String tota;
                if (res != null) {
                    res.moveToFirst(); // Because here's only one row data
                    total = res.getString(5); // Column 0 is the ID column
                }
                if(total.equals("1"))
                {
                    Toasty.error(context, "Quantity not zero...", Toast.LENGTH_SHORT, true).show();
                }else {
                    totalcount= Integer.valueOf(pricestring)-Integer.valueOf(pricecurrent);
                    Integer increase = Integer.parseInt(total) - 1;
                    String totalvalue = String.valueOf(increase);
                    boolean updated = easyDB.updateData(5, totalvalue)
                            .rowID(topProductdata.get(position).getRowid());
                    holder.quantity.setText(totalvalue);
                    Toasty.info(context, "Quantity decreases.....", Toast.LENGTH_SHORT, true).show();
                    String contotalcount=String.valueOf(totalcount);
                    pricetext.setText("");
                    pricetext.setText(contotalcount);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return topProductdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView imageView;
         TextView name,price,quantity;
          CardView cardView;
          ImageButton delete,add,minus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            add=itemView.findViewById(R.id.plusid);
            quantity=itemView.findViewById(R.id.quantityid);
            minus=itemView.findViewById(R.id.minusid);
            imageView=itemView.findViewById(R.id.imageid);
            name=itemView.findViewById(R.id.nameid);
            price=itemView.findViewById(R.id.priceid);
            cardView=itemView.findViewById(R.id.cardid);
            delete=itemView.findViewById(R.id.deleteid);

        }
    }
}
