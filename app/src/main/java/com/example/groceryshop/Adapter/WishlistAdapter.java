package com.example.groceryshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryshop.Modal.TopProductdata;
import com.example.groceryshop.ProductviewActivity;
import com.example.groceryshop.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
     Context context;
     ArrayList<TopProductdata> topProductdata;

    public WishlistAdapter(Context context, ArrayList<TopProductdata> topProductdata) {
        this.context = context;
        this.topProductdata = topProductdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.wishlist_listview,parent,false);
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
                Toasty.success(context, "Added Cart..", Toast.LENGTH_SHORT, true).show();
            }
        });
    /*    holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductviewActivity.class);
                intent.putExtra("image",topProductdata.get(position).getProductimage().toString());
                intent.putExtra("name",topProductdata.get(position).getProductname().toString());
                intent.putExtra("price",topProductdata.get(position).getProductprice().toString());
                context.startActivity(intent);

            }
        });  */
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageid);
            name=itemView.findViewById(R.id.nameid);
            description=itemView.findViewById(R.id.descriptionid);
            price=itemView.findViewById(R.id.priceid);
            wishlist=itemView.findViewById(R.id.wishlistid);
            add=itemView.findViewById(R.id.addid);
            cardView=itemView.findViewById(R.id.cartid);
        }
    }
}
